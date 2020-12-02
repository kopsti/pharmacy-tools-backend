package gr.appleton.ms.pharmacytools.common.crud;

import gr.appleton.ms.pharmacytools.authorization.persistence.UserDao;
import gr.appleton.ms.pharmacytools.common.constants.ExceptionMessages;
import gr.appleton.ms.pharmacytools.common.exceptions.GenericException;
import gr.appleton.ms.pharmacytools.common.utils.CommonService;
import gr.appleton.ms.pharmacytools.common.utils.GreekLatin;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The Abstract Service for CRUD operations.
 *
 * @param <M> the type parameter for Model
 * @param <D> the type parameter for Dao
 */
@Slf4j
public abstract class AbstractServiceCrud<M, D> implements CrudService<M, D> {

    private final CommonService common;

    /**
     * Instantiates a new Abstract Service CRUD.
     *
     * @param common the common service
     */
    @Autowired
    public AbstractServiceCrud(final CommonService common) {
        this.common = common;
    }

    @Override
    public M create(final String username, final M model) throws GenericException {
        try {
            return dao2Model(repository().save(model2Dao(model, null, common.getUserByUsername(username))));
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
            throw new GenericException(ExceptionMessages.CREATION_FAIL);
        }
    }

    @Override
    public M retrieveById(final long id) throws GenericException {
        return dao2Model(retrieveDaoById(id));
    }

    /**
     * Retrieve all list.
     *
     * @return the list
     * @throws GenericException the generic exception
     */
    @Override
    public List<M> retrieveAll(final String q, final boolean d) throws GenericException {
        final Iterable<D> daos = q != null
            ? repository().findByWildcard(GreekLatin.greek2latin(q), d)
            : repository().findByDeleted(d);
        try {
            return Lists.newArrayList(daos).stream().map(this::dao2Model).collect(Collectors.toList());
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
            throw new GenericException(ExceptionMessages.RETRIEVE_ALL_FAIL);
        }
    }

    @Override
    public D retrieveDaoById(final long id) throws GenericException {
        return repository().findById(id)
            .orElseThrow(() -> new GenericException(ExceptionMessages.RETRIEVE_BY_ID_FAIL + id));
    }

    @Override
    public M update(final String username, final long id, final M model)
        throws GenericException {
        try {
            return dao2Model(
                repository().save(model2Dao(model, retrieveDaoById(id), common.getUserByUsername(username))));
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
            throw new GenericException(ExceptionMessages.UPDATE_FAIL + id);
        }
    }

    @Override
    public void delete(final long id) throws GenericException {
        if (mayEnterBin() && alreadyInBin(id) || !mayEnterBin()) {
            actualDelete(id);
        } else {
            addInBin(id);
        }
    }

    private void actualDelete(final long id) throws GenericException {
        try {
            repository().deleteById(id);
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
            throw new GenericException(ExceptionMessages.DELETION_FAILURE + id);
        }
    }

    /**
     * Dao 2 model g.
     *
     * @param dao the dao
     * @return the g
     */
    public M dao2Model(final D dao) {
        //should be overwritten
        return null;
    }

    /**
     * Model 2 dao d.
     *
     * @param model   the model
     * @param dao     the dao
     * @param userDao the user dao
     * @return the d
     * @throws GenericException the generic exception
     */
    public D model2Dao(final M model, final D dao, final UserDao userDao) throws GenericException {
        //should be overwritten
        return null;
    }

    /**
     * May enter bin boolean.
     *
     * @return the boolean
     */
    public boolean mayEnterBin() {
        return false;
    }

    /**
     * Already in bin boolean.
     *
     * @param id the id
     * @return the boolean
     * @throws GenericException the generic exception
     */
    public boolean alreadyInBin(final long id) throws GenericException {
        return false;
    }

    /**
     * Add in bin.
     *
     * @param id the id
     * @throws GenericException the generic exception
     */
    public void addInBin(final long id) throws GenericException {
    }

}

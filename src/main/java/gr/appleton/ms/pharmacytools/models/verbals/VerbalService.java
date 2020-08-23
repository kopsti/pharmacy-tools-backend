package gr.appleton.ms.pharmacytools.models.verbals;

import com.querydsl.core.types.dsl.BooleanExpression;
import gr.appleton.ms.pharmacytools.authorization.persistence.UserDao;
import gr.appleton.ms.pharmacytools.common.constants.ExceptionMessages;
import gr.appleton.ms.pharmacytools.common.crud.AbstractServiceCrud;
import gr.appleton.ms.pharmacytools.common.crud.CrudService;
import gr.appleton.ms.pharmacytools.common.exceptions.GenericException;
import gr.appleton.ms.pharmacytools.common.utils.CommonService;
import gr.appleton.ms.pharmacytools.models.verbals.dto.VerbalDao;
import gr.appleton.ms.pharmacytools.models.verbals.dto.VerbalModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

/**
 * The service responsible to handle operations for Verbal instances management.
 */
@Service
@Slf4j
public class VerbalService extends AbstractServiceCrud<VerbalModel, VerbalDao, Object>
    implements CrudService<VerbalModel, VerbalDao> {

    private final VerbalRepository verbals;

    /**
     * Instantiates a new Verbal service.
     *
     * @param verbals the verbal repository
     * @param common  the common service
     */
    @Autowired
    public VerbalService(final VerbalRepository verbals, final CommonService common) {
        super(common);
        this.verbals = verbals;
    }

    @Override
    public CrudRepository<VerbalDao, Long> repository() {
        return verbals;
    }

    @Override
    public VerbalModel dao2Model(final VerbalDao dao) {
        final VerbalModel model = new VerbalModel();
        model.setId(dao.getId());
        model.setKey(dao.getKey());
        model.setValue(dao.getVerbal());
        return model;
    }

    @Override
    public VerbalDao model2Dao(final VerbalModel model, VerbalDao dao, final UserDao userDao) {

        if (dao == null) {
            dao = new VerbalDao();
        }
        dao.setKey(model.getKey());
        dao.setVerbal(model.getValue());
        return dao;
    }

    @Override
    public Iterable<VerbalDao> queryDaos(final BooleanExpression exp) {
        return verbals.findAll();
    }

    @Override
    public VerbalDao retrieveDaoById(long id) throws GenericException {
        return verbals.findById(id).orElseThrow(() -> new GenericException(ExceptionMessages.RETRIEVE_BY_ID_FAIL + id));
    }

}

package gr.appleton.ms.pharmacytools.common.crud;

import gr.appleton.ms.pharmacytools.common.exceptions.GenericException;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * The interface Crud service.
 *
 * @param <M> the type parameter for Model
 * @param <D> the type parameter for Dao
 */
public interface CrudService<M, D> {

    /**
     * Create an entity.
     *
     * @param username the user who creates the entity
     * @param model    the model to be created
     * @return the entity that was created
     * @throws GenericException the generic exception
     */
    M create(final String username, final M model) throws GenericException;

    /**
     * Retrieve dao by its id.
     *
     * @param id the id
     * @return the dao that was retrieved
     * @throws GenericException the generic exception
     */
    D retrieveDaoById(final long id) throws GenericException;

    /**
     * Retrieve entity by its id.
     *
     * @param id the entity's id
     * @return the entity that was retrieved
     * @throws GenericException the generic exception
     */
    M retrieveById(final long id) throws GenericException;

    /**
     * Retrieve all entities.
     *
     * @param q the query to filter results
     * @return the entities that were retrieved
     * @throws GenericException the generic exception
     */
    List<M> retrieveAll(final String q) throws GenericException;

    /**
     * Update an entity.
     *
     * @param username the user who updates the entity
     * @param id       the entity's id
     * @param model    the model with the updated values for the entity
     * @return the entity that was updated
     * @throws GenericException the generic exception
     */
    M update(final String username, final long id, final M model) throws GenericException;

    /**
     * Delete an entity by its id.
     *
     * @param id the entity's id
     * @throws GenericException the generic exception
     */
    void delete(final long id) throws GenericException;

    /**
     * Repository crud repository.
     *
     * @return the crud repository
     */
    CrudRepository<D, Long> repository();

}

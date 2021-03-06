package gr.appleton.ms.pharmacytools.common.crud;

import gr.appleton.ms.pharmacytools.common.constants.Constants;
import gr.appleton.ms.pharmacytools.common.dto.CommonModel;
import gr.appleton.ms.pharmacytools.common.enumerations.ClientResponses;
import gr.appleton.ms.pharmacytools.common.exceptions.GenericException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * The Abstract Controller for CRUD operations.
 *
 * @param <R> the type parameter for Resource
 * @param <M> the type parameter for Model
 * @param <D> the type parameter for Dao
 */
public abstract class AbstractControllerCrud<R, M extends AbstractModel, D> implements CrudController<M, D> {

    private static final String AGENT = Constants.AGENT;
    private static final ClientResponses OK = ClientResponses.OK;

    /**
     * Create an entity.
     *
     * @param username the user who creates the entity
     * @param model    the model to be created
     * @return the entity that was created
     * @throws GenericException the generic exception
     */
    @PostMapping
    public ResponseEntity<R> create(@RequestHeader(AGENT) final String username,
                                    @RequestBody final M model) throws GenericException {

        final M createdModel = service().create(username, model);

        return ResponseEntity.created(
            MvcUriComponentsBuilder.fromController(getClass()).path("/{id}").buildAndExpand(createdModel.getId())
                .toUri()).body(getResource(createdModel));
    }

    /**
     * Retrieve an entity by its id.
     *
     * @param id the entity's id
     * @return the entity that was retrieved
     * @throws GenericException the generic exception
     */
    @GetMapping("{id}")
    public ResponseEntity<R> retrieveById(@PathVariable final long id) throws GenericException {
        return ResponseEntity.ok(getResource(service().retrieveById(id)));
    }

    /**
     * Retrieve all entities.
     *
     * @param q the wildcard search
     * @return the entities that were retrieved
     * @throws GenericException the generic exception
     */
    @GetMapping
    public ResponseEntity<R> retrieveAll(@RequestParam(value = "q", required = false) final String q,
                                         @RequestParam(value = "d", required = false, defaultValue = "false")
                                         final boolean d)
        throws GenericException {
        return ResponseEntity.ok(getListResource(new ArrayList<>(service().retrieveAll(q, d))));
    }

    /**
     * Update an entity.
     *
     * @param username the user who updates the entity
     * @param id       the entity's id
     * @param model    the model with the updated values for the entity
     * @return the entity that was updated
     * @throws GenericException the generic exception
     */
    @PutMapping("{id}")
    public ResponseEntity<R> update(@RequestHeader(AGENT) final String username, @PathVariable final long id,
                                    @RequestBody final M model) throws GenericException {

        return ResponseEntity.ok(getResource(service().update(username, id, model)));
    }

    /**
     * Delete an entity by its id.
     *
     * @param id the entity's id
     * @return the acknowledgment response
     * @throws GenericException the generic exception
     */
    @DeleteMapping("{id}")
    public ResponseEntity<CommonModel> delete(@PathVariable final long id) throws GenericException {

        service().delete(id);

        return ResponseEntity.ok(new CommonModel(null, OK.getCode(), OK.getMessage()));
    }

    /**
     * When overwritten, will return the resource for an entity.
     *
     * @param model the model
     * @return the resource to be returned
     */
    public R getResource(final M model) {
        //should be overwritten
        return null;
    }

    /**
     * When overwritten, will return the list of resources for a type.
     *
     * @param model the model
     * @return the list of resource to be returned
     */
    public R getListResource(final List<M> model) {
        //should be overwritten
        return null;
    }

}

package gr.appleton.ms.pharmacytools.common.crud;

/**
 * The interface Crud controller.
 *
 * @param <M> the type parameter for Model
 * @param <D> the type parameter for Dao
 */
public interface CrudController<M, D> {

    /**
     * Service crud service.
     *
     * @return the crud service
     */
    CrudService<M, D> service();

}

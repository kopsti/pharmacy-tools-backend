package gr.appleton.ms.pharmacytools.common.crud;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * The interface My crud repository.
 *
 * @param <D> the type parameter
 * @param <L> the type parameter
 */
@Repository
public interface MyCrudRepository<D, L> extends CrudRepository<D, L> {

    /**
     * Find by wild card iterable.
     *
     * @param wildcard the wildcard
     * @return the iterable
     */
    Iterable<D> findByWildCard(@Param("wildcard") String wildcard);

}

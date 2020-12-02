package gr.appleton.ms.pharmacytools.common.crud;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

/**
 * The interface My crud repository.
 *
 * @param <D> the type parameter
 * @param <L> the type parameter
 */
@NoRepositoryBean
public interface MyCrudRepository<D, L> extends CrudRepository<D, L> {

    /**
     * Find by wild card iterable.
     *
     * @param wildcard the wildcard
     * @return the iterable
     */
    Iterable<D> findByWildcard(@Param("wildcard") String wildcard);

}

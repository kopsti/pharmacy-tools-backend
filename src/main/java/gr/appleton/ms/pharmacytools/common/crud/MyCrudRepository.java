package gr.appleton.ms.pharmacytools.common.crud;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface MyCrudRepository<D, L> extends CrudRepository<D, L> {

    Iterable<D> findByWildCard(@Param("wildcard") String wildcard);

}

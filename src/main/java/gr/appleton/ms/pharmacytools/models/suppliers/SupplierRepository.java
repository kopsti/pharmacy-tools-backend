package gr.appleton.ms.pharmacytools.models.suppliers;

import gr.appleton.ms.pharmacytools.common.crud.MyCrudRepository;
import gr.appleton.ms.pharmacytools.models.suppliers.dto.SupplierDao;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * The repository responsible to handle DB operations for Supplier records management.
 */
@Repository
public interface SupplierRepository extends MyCrudRepository<SupplierDao, Long> {

    @Override
    Iterable<SupplierDao> findByWildcard(@Param("wildcard") String wildcard);

}

package gr.appleton.ms.pharmacytools.models.suppliers;

import gr.appleton.ms.pharmacytools.models.suppliers.dto.SupplierDao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The repository responsible to handle DB operations for Supplier records management.
 */
@Repository
public interface SupplierRepository extends CrudRepository<SupplierDao, Long> {
}

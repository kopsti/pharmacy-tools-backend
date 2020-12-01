package gr.appleton.ms.pharmacytools.models.customers;

import gr.appleton.ms.pharmacytools.models.customers.dto.CustomerDao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The repository responsible to handle DB operations for Customer records management.
 */
@Repository
public interface CustomerRepository extends CrudRepository<CustomerDao, Long> {

}

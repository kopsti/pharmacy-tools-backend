package gr.appleton.ms.pharmacytools.models.customers;

import gr.appleton.ms.pharmacytools.common.crud.MyCrudRepository;
import gr.appleton.ms.pharmacytools.models.customers.dto.CustomerDao;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * The repository responsible to handle DB operations for Customer records management.
 */
@Repository
public interface CustomerRepository extends MyCrudRepository<CustomerDao, Long> {

    @Override
    Iterable<CustomerDao> findByWildcard(@Param("wildcard") String wildcard);

}

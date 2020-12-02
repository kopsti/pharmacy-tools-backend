package gr.appleton.ms.pharmacytools.models.customers;

import gr.appleton.ms.pharmacytools.common.crud.MyCrudRepository;
import gr.appleton.ms.pharmacytools.models.customers.dto.CustomerDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * The repository responsible to handle DB operations for Customer records management.
 */
@Repository
public interface CustomerRepository extends MyCrudRepository<CustomerDao, Long> {

    @Override
    @Query("from CustomerDao where "
        + "qFirstName like concat('%',:wildcard,'%') "
        + "or qLastName like concat('%',:wildcard,'%') "
        + "or qAddress like concat('%',:wildcard,'%') "
        + "or mobilePhoneNumber like concat('%',:wildcard,'%') "
        + "or homePhoneNumber like concat('%',:wildcard,'%') "
        + "or email like concat('%',:wildcard,'%') "
        + "or qComments like concat('%',:wildcard,'%')")
    Iterable<CustomerDao> findByWildCard(@Param("wildcard") String wildcard);

}

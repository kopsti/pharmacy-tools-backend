package gr.appleton.ms.pharmacytools.models.orders;

import gr.appleton.ms.pharmacytools.models.orders.dto.OrderDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * The repository responsible to handle DB operations for Selling Order records management.
 */
@Repository
public interface OrderRepository extends CrudRepository<OrderDao, Long> {

    @Query("from OrderDao where "
        + "qProduct like concat('%',:wildcard,'%') "
        + "or supplier.qTitle like concat('%',:wildcard,'%') "
        + "or customer.qLastName like concat('%',:wildcard,'%') "
        + "or customer.homePhoneNumber like concat('%',:wildcard,'%') "
        + "or customer.mobilePhoneNumber like concat('%',:wildcard,'%') "
        + "or customer.email like concat('%',:wildcard,'%') "
        + "or qComments like concat('%',:wildcard,'%')")
    Iterable<OrderDao> findByWildCard(@Param("wildcard") String wildcard);

}

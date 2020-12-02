package gr.appleton.ms.pharmacytools.models.orders;

import gr.appleton.ms.pharmacytools.common.crud.MyCrudRepository;
import gr.appleton.ms.pharmacytools.models.orders.dto.OrderDao;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * The repository responsible to handle DB operations for Selling Order records management.
 */
@Repository
public interface OrderRepository extends MyCrudRepository<OrderDao, Long> {

    @Override
    Iterable<OrderDao> findByWildcard(@Param("wildcard") String wildcard, boolean deleted);

}

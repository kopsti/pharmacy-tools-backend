package gr.appleton.ms.pharmacytools.models.orders;

import gr.appleton.ms.pharmacytools.models.orders.dto.OrderDao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The repository responsible to handle DB operations for Selling Order records management.
 */
@Repository
public interface OrderRepository extends CrudRepository<OrderDao, Long> {
}

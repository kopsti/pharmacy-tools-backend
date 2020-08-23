package gr.appleton.ms.pharmacytools.models.orders;

import com.querydsl.core.types.dsl.BooleanExpression;
import gr.appleton.ms.pharmacytools.models.orders.dto.OrderDao;
import gr.appleton.ms.pharmacytools.models.orders.dto.QOrderDao;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The repository responsible to handle DB operations for Selling Order records management.
 */
@Repository
public interface OrderRepository extends CrudRepository<OrderDao, Long>, QuerydslPredicateExecutor<OrderDao>,
    QuerydslBinderCustomizer<QOrderDao> {

    @Override
    default void customize(final QuerydslBindings bindings, final QOrderDao root) {
        bindings.bind(root.deleted).first(BooleanExpression::eq);
        bindings.bind(root.supplier.deleted).first(BooleanExpression::eq);
        bindings.bind(root.customer.deleted).first(BooleanExpression::eq);
        bindings.bind(root.customer.vip).first(BooleanExpression::eq);
    }

}

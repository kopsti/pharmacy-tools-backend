package gr.appleton.ms.pharmacytools.models.customers;

import com.querydsl.core.types.dsl.BooleanExpression;
import gr.appleton.ms.pharmacytools.models.customers.dto.CustomerDao;
import gr.appleton.ms.pharmacytools.models.customers.dto.QCustomerDao;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The repository responsible to handle DB operations for Customer records management.
 */
@Repository
public interface CustomerRepository extends CrudRepository<CustomerDao, Long>, QuerydslPredicateExecutor<CustomerDao>,
    QuerydslBinderCustomizer<QCustomerDao> {

    @Override
    default void customize(final QuerydslBindings bindings, final QCustomerDao root) {
        bindings.bind(root.deleted).first(BooleanExpression::eq);
        bindings.bind(root.vip).first(BooleanExpression::eq);
    }

}

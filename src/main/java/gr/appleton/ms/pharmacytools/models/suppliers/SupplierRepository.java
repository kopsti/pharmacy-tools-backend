package gr.appleton.ms.pharmacytools.models.suppliers;

import com.querydsl.core.types.dsl.BooleanExpression;
import gr.appleton.ms.pharmacytools.models.suppliers.dto.QSupplierDao;
import gr.appleton.ms.pharmacytools.models.suppliers.dto.SupplierDao;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The repository responsible to handle DB operations for Supplier records management.
 */
@Repository
public interface SupplierRepository extends CrudRepository<SupplierDao, Long>, QuerydslPredicateExecutor<SupplierDao>,
    QuerydslBinderCustomizer<QSupplierDao> {

    @Override
    default void customize(final QuerydslBindings bindings, final QSupplierDao root) {
        bindings.bind(root.deleted).first(BooleanExpression::eq);
    }

}

package gr.appleton.ms.pharmacytools.models.accounts;

import com.querydsl.core.types.dsl.BooleanExpression;
import gr.appleton.ms.pharmacytools.models.accounts.dto.BankAccountDao;
import gr.appleton.ms.pharmacytools.models.accounts.dto.QBankAccountDao;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The repository responsible to handle DB operations for Bank Account records management.
 */
@Repository
public interface BankAccountRepository
    extends CrudRepository<BankAccountDao, Long>, QuerydslPredicateExecutor<BankAccountDao>,
    QuerydslBinderCustomizer<QBankAccountDao> {

    @Override
    default void customize(final QuerydslBindings bindings, final QBankAccountDao root) {
        bindings.bind(root.deleted).first(BooleanExpression::eq);
        bindings.bind(root.supplier.deleted).first(BooleanExpression::eq);
    }

}

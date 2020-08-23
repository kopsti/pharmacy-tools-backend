package gr.appleton.ms.pharmacytools.models.accounts.filtering;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import gr.appleton.ms.pharmacytools.common.dto.SearchCriteria;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The type Bank account predicates builder.
 */
public final class BankAccountPredicatesBuilder {

    private final List<SearchCriteria> params;

    /**
     * Instantiates a new Bank account predicates builder.
     */
    public BankAccountPredicatesBuilder() {
        params = new ArrayList<>();
    }

    /**
     * With bank account predicates builder.
     *
     * @param key       the key
     * @param operation the operation
     * @param value     the value
     * @return the bank account predicates builder
     */
    public BankAccountPredicatesBuilder with(final String key, final String operation, final Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }

    /**
     * Build boolean expression.
     *
     * @return the boolean expression
     */
    public BooleanExpression build() {
        if (params.isEmpty()) {
            return null;
        }

        final List<BooleanExpression> predicates = params.stream().map(param -> {
            final BankAccountPredicate predicate = new BankAccountPredicate(param);
            return predicate.getPredicate();
        }).filter(Objects::nonNull).collect(Collectors.toList());

        BooleanExpression result = Expressions.asBoolean(true).isTrue();

        for (BooleanExpression predicate : predicates) {
            result = result.and(predicate);
        }

        return result;
    }

}

package gr.appleton.ms.pharmacytools.models.suppliers.filtering;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import gr.appleton.ms.pharmacytools.common.dto.SearchCriteria;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The type Supplier predicates builder.
 */
public final class SupplierPredicatesBuilder {

    private final List<SearchCriteria> params;

    /**
     * Instantiates a new Supplier predicates builder.
     */
    public SupplierPredicatesBuilder() {
        params = new ArrayList<>();
    }

    /**
     * With supplier predicates builder.
     *
     * @param key       the key
     * @param operation the operation
     * @param value     the value
     * @return the supplier predicates builder
     */
    public SupplierPredicatesBuilder with(final String key, final String operation, final Object value) {
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
            final SupplierPredicate predicate = new SupplierPredicate(param);
            return predicate.getPredicate();
        }).filter(Objects::nonNull).collect(Collectors.toList());

        BooleanExpression result = Expressions.asBoolean(true).isTrue();

        for (BooleanExpression predicate : predicates) {
            result = result.and(predicate);
        }

        return result;
    }

}

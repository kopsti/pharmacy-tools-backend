package gr.appleton.ms.pharmacytools.models.notes.filtering;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import gr.appleton.ms.pharmacytools.common.dto.SearchCriteria;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The type Note predicates builder.
 */
public final class NotePredicatesBuilder {

    private final List<SearchCriteria> params;

    /**
     * Instantiates a new Note predicates builder.
     */
    public NotePredicatesBuilder() {
        params = new ArrayList<>();
    }

    /**
     * With note predicates builder.
     *
     * @param key       the key
     * @param operation the operation
     * @param value     the value
     * @return the note predicates builder
     */
    public NotePredicatesBuilder with(final String key, final String operation, final Object value) {
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
            final NotePredicate predicate = new NotePredicate(param);
            return predicate.getPredicate();
        }).filter(Objects::nonNull).collect(Collectors.toList());

        BooleanExpression result = Expressions.asBoolean(true).isTrue();

        for (BooleanExpression predicate : predicates) {
            result = result.and(predicate);
        }

        return result;
    }

}

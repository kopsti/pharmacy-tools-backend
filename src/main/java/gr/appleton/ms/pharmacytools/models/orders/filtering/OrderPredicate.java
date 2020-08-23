package gr.appleton.ms.pharmacytools.models.orders.filtering;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import gr.appleton.ms.pharmacytools.common.dto.SearchCriteria;
import gr.appleton.ms.pharmacytools.common.utils.Utilities;
import gr.appleton.ms.pharmacytools.models.customers.dto.CustomerDao;

/**
 * The type Order predicate.
 */
public class OrderPredicate {

    private final SearchCriteria criteria;

    /**
     * Instantiates a new Order predicate.
     *
     * @param criteria the criteria
     */
    public OrderPredicate(final SearchCriteria criteria) {
        this.criteria = criteria;
    }

    /**
     * Gets predicate.
     *
     * @return the predicate
     */
    public BooleanExpression getPredicate() {
        return Utilities.handlePredicates(new PathBuilder<>(CustomerDao.class, "orderDao"), criteria);
    }

}

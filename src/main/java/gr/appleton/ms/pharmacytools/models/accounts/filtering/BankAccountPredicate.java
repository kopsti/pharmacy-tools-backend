package gr.appleton.ms.pharmacytools.models.accounts.filtering;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import gr.appleton.ms.pharmacytools.common.dto.SearchCriteria;
import gr.appleton.ms.pharmacytools.common.utils.Utilities;
import gr.appleton.ms.pharmacytools.models.customers.dto.CustomerDao;

/**
 * The type Bank account predicate.
 */
public class BankAccountPredicate {

    private final SearchCriteria criteria;

    /**
     * Instantiates a new Bank account predicate.
     *
     * @param criteria the criteria
     */
    public BankAccountPredicate(final SearchCriteria criteria) {
        this.criteria = criteria;
    }

    /**
     * Gets predicate.
     *
     * @return the predicate
     */
    public BooleanExpression getPredicate() {
        return Utilities.handlePredicates(new PathBuilder<>(CustomerDao.class, "bankAccountDao"), criteria);
    }

}

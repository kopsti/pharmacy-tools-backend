package gr.appleton.ms.pharmacytools.common.utils;

import static org.hibernate.query.criteria.internal.ValueHandlerFactory.isNumeric;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringPath;
import gr.appleton.ms.pharmacytools.common.dto.SearchCriteria;
import gr.appleton.ms.pharmacytools.models.accounts.dto.BankAccountDao;
import gr.appleton.ms.pharmacytools.models.accounts.filtering.BankAccountPredicatesBuilder;
import gr.appleton.ms.pharmacytools.models.customers.dto.CustomerDao;
import gr.appleton.ms.pharmacytools.models.customers.filtering.CustomerPredicatesBuilder;
import gr.appleton.ms.pharmacytools.models.notes.dto.NoteDao;
import gr.appleton.ms.pharmacytools.models.notes.filtering.NotePredicatesBuilder;
import gr.appleton.ms.pharmacytools.models.orders.dto.OrderDao;
import gr.appleton.ms.pharmacytools.models.orders.filtering.OrderPredicatesBuilder;
import gr.appleton.ms.pharmacytools.models.suppliers.dto.SupplierDao;
import gr.appleton.ms.pharmacytools.models.suppliers.filtering.SupplierPredicatesBuilder;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Utility class with static useful methods.
 */
public final class Utilities {

    private Utilities() {
    }

    /**
     * Gets calendar with offset.
     *
     * @param type   the type
     * @param offset the offset
     * @return the calendar with offset
     */
    public static Calendar getCalendarWithOffset(final int type, final int offset) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(type, offset);
        return cal;
    }

    /**
     * Gets expression.
     *
     * @param <T>  the type parameter
     * @param type the type
     * @param q    the q
     * @return the expression
     */
    public static <T> BooleanExpression getExpression(Class<T> type, final String q) {

        if (q == null) {
            return null;
        }

        final Matcher matcher = Pattern.compile("(\\w+?)([:<>])(\\w+?),").matcher(q + ",");

        if (type == BankAccountPredicatesBuilder.class) {
            final BankAccountPredicatesBuilder builder = new BankAccountPredicatesBuilder();
            while (matcher.find()) {
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
            }
            return builder.build();
        } else if (type == CustomerPredicatesBuilder.class) {
            final CustomerPredicatesBuilder builder = new CustomerPredicatesBuilder();
            while (matcher.find()) {
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
            }
            return builder.build();
        } else if (type == NotePredicatesBuilder.class) {
            final NotePredicatesBuilder builder = new NotePredicatesBuilder();
            while (matcher.find()) {
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
            }
            return builder.build();
        } else if (type == OrderPredicatesBuilder.class) {
            final OrderPredicatesBuilder builder = new OrderPredicatesBuilder();
            while (matcher.find()) {
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
            }
            return builder.build();
        } else if (type == SupplierPredicatesBuilder.class) {
            final SupplierPredicatesBuilder builder = new SupplierPredicatesBuilder();
            while (matcher.find()) {
                builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
            }
            return builder.build();
        } else {
            return null;
        }
    }

    /**
     * Handle predicates for querying.
     *
     * @param <T>      the type parameter
     * @param path     the path
     * @param criteria the search criteria
     * @return the boolean expression
     */
    public static <T> BooleanExpression handlePredicates(final PathBuilder<T> path, final SearchCriteria criteria) {
        if ("any".equalsIgnoreCase(criteria.getKey())) {
            return handleAny(path, criteria);
        }
        if (isNumeric(criteria.getValue().toString())) {
            return handleNumeric(path, criteria);
        } else if ("true".equals(criteria.getValue()) || "false".equals(criteria.getValue())) {
            return handleBoolean(path, criteria);
        } else {
            return handleString(path, criteria);
        }
    }

    private static <T> BooleanExpression handleAny(final PathBuilder<T> entityPath, final SearchCriteria criteria) {
        if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (entityPath.getType() == CustomerDao.class) {
                final StringPath firstName = entityPath.getString("qFirstName");
                final StringPath lastName = entityPath.getString("qLastName");
                final StringPath address = entityPath.getString("qAddress");
                final StringPath homePhoneNumber = entityPath.getString("homePhoneNumber");
                final StringPath mobilePhoneNumber = entityPath.getString("mobilePhoneNumber");
                final StringPath email = entityPath.getString("email");
                final StringPath comments = entityPath.getString("qComments");

                return firstName.containsIgnoreCase(GreekLatin.greek2latin(criteria.getValue().toString()))
                    .or(lastName.containsIgnoreCase(GreekLatin.greek2latin(criteria.getValue().toString())))
                    .or(address.containsIgnoreCase(GreekLatin.greek2latin(criteria.getValue().toString())))
                    .or(homePhoneNumber.containsIgnoreCase(GreekLatin.greek2latin(criteria.getValue().toString())))
                    .or(mobilePhoneNumber.containsIgnoreCase(GreekLatin.greek2latin(criteria.getValue().toString())))
                    .or(email.containsIgnoreCase(GreekLatin.greek2latin(criteria.getValue().toString())))
                    .or(comments.containsIgnoreCase(GreekLatin.greek2latin(criteria.getValue().toString())));
            } else if (entityPath.getType() == NoteDao.class) {
                final StringPath content = entityPath.getString("qContent");
                final StringPath comments = entityPath.getString("qComments");

                return content.containsIgnoreCase(GreekLatin.greek2latin(criteria.getValue().toString()))
                    .or(comments.containsIgnoreCase(GreekLatin.greek2latin(criteria.getValue().toString())));
            } else if (entityPath.getType() == BankAccountDao.class) {
                final StringPath number = entityPath.getString("number");
                final StringPath iban = entityPath.getString("iban");
                final StringPath comments = entityPath.getString("qComments");

                return number.containsIgnoreCase(GreekLatin.greek2latin(criteria.getValue().toString()))
                    .or(iban.containsIgnoreCase(GreekLatin.greek2latin(criteria.getValue().toString())))
                    .or(comments.containsIgnoreCase(GreekLatin.greek2latin(criteria.getValue().toString())));
            } else if (entityPath.getType() == SupplierDao.class) {
                final StringPath title = entityPath.getString("qTitle");
                final StringPath description = entityPath.getString("qDescription");
                final StringPath email = entityPath.getString("email");
                final StringPath phoneNumber = entityPath.getString("phoneNumber");
                final StringPath taxId = entityPath.getString("taxId");
                final StringPath taxAuthority = entityPath.getString("taxAuthority");
                final StringPath comments = entityPath.getString("qComments");

                return title.containsIgnoreCase(GreekLatin.greek2latin(criteria.getValue().toString()))
                    .or(description.containsIgnoreCase(GreekLatin.greek2latin(criteria.getValue().toString())))
                    .or(email.containsIgnoreCase(GreekLatin.greek2latin(criteria.getValue().toString())))
                    .or(phoneNumber.containsIgnoreCase(GreekLatin.greek2latin(criteria.getValue().toString())))
                    .or(taxId.containsIgnoreCase(GreekLatin.greek2latin(criteria.getValue().toString())))
                    .or(taxAuthority.containsIgnoreCase(GreekLatin.greek2latin(criteria.getValue().toString())))
                    .or(comments.containsIgnoreCase(GreekLatin.greek2latin(criteria.getValue().toString())));
            } else if (entityPath.getType() == OrderDao.class) {
                final StringPath product = entityPath.getString("qProduct");
                final StringPath comments = entityPath.getString("qComments");

                return product.containsIgnoreCase(GreekLatin.greek2latin(criteria.getValue().toString()))
                    .or(comments.containsIgnoreCase(GreekLatin.greek2latin(criteria.getValue().toString())));
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    private static <T> BooleanExpression handleNumeric(final PathBuilder<T> entityPath, final SearchCriteria criteria) {
        if (Long.TYPE.isInstance(criteria.getValue().toString())) {
            final NumberPath<Long> path = entityPath.getNumber(criteria.getKey(), Long.class);
            final Long value = Long.parseLong(criteria.getValue().toString());
            return switchNumericCriteria(criteria, path, value);
        } else if (Float.TYPE.isInstance(criteria.getValue().toString())) {
            final NumberPath<Float> path = entityPath.getNumber(criteria.getKey(), Float.class);
            final Float value = Float.parseFloat(criteria.getValue().toString());
            return switchNumericCriteria(criteria, path, value);
        } else if (Integer.TYPE.isInstance(criteria.getValue().toString())) {
            final NumberPath<Integer> path = entityPath.getNumber(criteria.getKey(), Integer.class);
            final Integer value = Integer.parseInt(criteria.getValue().toString());
            return switchNumericCriteria(criteria, path, value);
        } else {
            return null;
        }
    }

    private static <T extends Number & Comparable<?>> BooleanExpression switchNumericCriteria(
        final SearchCriteria criteria, NumberPath<T> path,
        final T value) {
        switch (criteria.getOperation()) {
            case ":":
                return path.eq(value);
            case ">":
                return path.goe(value);
            case "<":
                return path.loe(value);
            default:
                return null;
        }
    }

    private static <T> BooleanExpression handleString(final PathBuilder<T> entityPath, final SearchCriteria criteria) {
        final StringPath path = entityPath.getString(criteria.getKey());
        if (criteria.getOperation().equalsIgnoreCase(":")) {
            return path.containsIgnoreCase(GreekLatin.greek2latin(criteria.getValue().toString()));
        } else {
            return null;
        }
    }

    private static <T> BooleanExpression handleBoolean(final PathBuilder<T> entityPath, final SearchCriteria criteria) {
        return entityPath.getBoolean(criteria.getKey()).eq("true".equals(criteria.getValue()));
    }

}

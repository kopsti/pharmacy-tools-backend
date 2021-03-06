package gr.appleton.ms.pharmacytools.models.orders.dto;

import gr.appleton.ms.pharmacytools.authorization.persistence.UserDao;
import gr.appleton.ms.pharmacytools.common.constants.DbConstants;
import gr.appleton.ms.pharmacytools.models.customers.dto.CustomerDao;
import gr.appleton.ms.pharmacytools.models.suppliers.dto.SupplierDao;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The model used for mapping DB Selling Order instances.
 */
@Getter
@Setter
@NamedQuery(
    name = "OrderDao.findByWildcard",
    query = "select o from OrderDao o where "
        + "o.deleted = :deleted "
        + "and (o.qProduct like concat('%',:wildcard,'%') "
        + "or o.supplier.qTitle like concat('%',:wildcard,'%') "
        + "or o.customer.qFirstName like concat('%',:wildcard,'%') "
        + "or o.customer.qLastName like concat('%',:wildcard,'%') "
        + "or o.customer.homePhoneNumber like concat('%',:wildcard,'%') "
        + "or o.customer.mobilePhoneNumber like concat('%',:wildcard,'%') "
        + "or o.customer.email like concat('%',:wildcard,'%'))"
)
@Entity
@Table(schema = DbConstants.RETMAN_SCHEMA, name = DbConstants.ORDERS)
public final class OrderDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = DbConstants.OWNER_ID)
    private UserDao owner;

    @ManyToOne
    @JoinColumn(name = DbConstants.SUPPLIER_ID)
    private SupplierDao supplier;

    @ManyToOne
    @JoinColumn(name = DbConstants.CUSTOMER_ID)
    private CustomerDao customer;

    private String type;
    private String status;
    private String product;
    private String qProduct;
    private Date expirationTimestamp;
    private Date completeTimestamp;
    private boolean deleted;
    private Date deleteTimestamp;
    private String comments;

    @Column(insertable = false) private Date insertTimestamp;
    @Column(insertable = false) private Date updateTimestamp;

}

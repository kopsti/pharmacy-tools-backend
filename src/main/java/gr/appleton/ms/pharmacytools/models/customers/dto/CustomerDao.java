package gr.appleton.ms.pharmacytools.models.customers.dto;

import gr.appleton.ms.pharmacytools.authorization.persistence.UserDao;
import gr.appleton.ms.pharmacytools.common.constants.DbConstants;
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
 * The model used for mapping DB Customer instances.
 */
@Getter
@Setter
@NamedQuery(
    name = "CustomerDao.findByWildcard",
    query = "select c from CustomerDao c where "
        + "c.deleted = :deleted "
        + "and (c.qFirstName like concat('%',:wildcard,'%') "
        + "or c.qLastName like concat('%',:wildcard,'%') "
        + "or c.qAddress like concat('%',:wildcard,'%') "
        + "or c.mobilePhoneNumber like concat('%',:wildcard,'%') "
        + "or c.homePhoneNumber like concat('%',:wildcard,'%') "
        + "or c.email like concat('%',:wildcard,'%'))"
)
@Entity
@Table(schema = DbConstants.RETMAN_SCHEMA, name = DbConstants.CUSTOMERS)
public final class CustomerDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = DbConstants.OWNER_ID)
    private UserDao owner;

    private String firstName;
    private String qFirstName;
    private String lastName;
    private String qLastName;
    private String address;
    private String qAddress;
    private boolean vip;
    private String homePhoneNumber;
    private String mobilePhoneNumber;
    private String email;
    private boolean deleted;
    private Date deleteTimestamp;
    private String comments;

    @Column(insertable = false) private Date insertTimestamp;
    @Column(insertable = false) private Date updateTimestamp;

}

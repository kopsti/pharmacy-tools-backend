package gr.appleton.ms.pharmacytools.models.suppliers.dto;

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
 * The model used for mapping DB Supplier instances.
 */
@Getter
@Setter
@NamedQuery(
    name = "SupplierDao.findByWildcard",
    query = "select s from SupplierDao s where "
        + "s.qDescription like concat('%',:wildcard,'%') "
        + "or s.qTitle like concat('%',:wildcard,'%') "
        + "or s.email like concat('%',:wildcard,'%') "
        + "or s.phoneNumber like concat('%',:wildcard,'%') "
        + "or s.taxAuthority like concat('%',:wildcard,'%') "
        + "or s.taxId like concat('%',:wildcard,'%') "
        + "or s.qComments like concat('%',:wildcard,'%')"
)
@Entity
@Table(schema = DbConstants.RETMAN_SCHEMA, name = DbConstants.SUPPLIERS)
public final class SupplierDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = DbConstants.OWNER_ID)
    private UserDao owner;

    private String title;
    private String qTitle;
    private String description;
    private String qDescription;
    private String email;
    private String phoneNumber;
    private String taxId;
    private String taxAuthority;
    private boolean deleted;
    private Date deleteTimestamp;
    private String comments;
    private String qComments;

    @Column(insertable = false) private Date insertTimestamp;
    @Column(insertable = false) private Date updateTimestamp;

}

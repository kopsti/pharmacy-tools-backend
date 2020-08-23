package gr.appleton.ms.pharmacytools.authorization.persistence;

import gr.appleton.ms.pharmacytools.common.constants.DbConstants;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The type User dao.
 */
@Getter
@Setter
@Entity
@Table(schema = DbConstants.RETMAN_SCHEMA, name = DbConstants.USERS)
public final class UserDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String role;
    private String email;
    private String pass;
    private boolean active;
    private Date lastLoginTimestamp;
    private Date lastLogoutTimestamp;
    private Date passInteractionTimestamp;
    private String comments;
    private String qComments;

    @Column(insertable = false) private Date insertTimestamp;
    @Column(insertable = false) private Date updateTimestamp;

}

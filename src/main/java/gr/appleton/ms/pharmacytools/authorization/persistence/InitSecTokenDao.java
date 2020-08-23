package gr.appleton.ms.pharmacytools.authorization.persistence;

import gr.appleton.ms.pharmacytools.common.constants.DbConstants;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The type Init sec token dao.
 */
@Getter
@Setter
@Entity
@Table(schema = DbConstants.RETMAN_SCHEMA, name = DbConstants.PASS_SEC_TOKENS)
public class InitSecTokenDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = DbConstants.OWNER_ID)
    private UserDao owner;

    private String flow;
    private UUID initToken;
    private Date initTokenExpirationTimestamp;
    private UUID secToken;
    private Date secTokenExpirationTimestamp;

    @Column(insertable = false) private Date insertTimestamp;

}

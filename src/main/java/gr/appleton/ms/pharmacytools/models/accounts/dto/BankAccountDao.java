package gr.appleton.ms.pharmacytools.models.accounts.dto;

import gr.appleton.ms.pharmacytools.authorization.persistence.UserDao;
import gr.appleton.ms.pharmacytools.common.constants.DbConstants;
import gr.appleton.ms.pharmacytools.models.banks.dto.BankDao;
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
import javax.persistence.Table;

/**
 * The model used for mapping DB Bank Account instances.
 */
@Getter
@Setter
@Entity
@Table(schema = DbConstants.RETMAN_SCHEMA, name = DbConstants.BANK_ACCOUNTS)
public class BankAccountDao {

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
    @JoinColumn(name = DbConstants.BANK_ID)
    private BankDao bank;

    private String iban;
    private String number;
    private boolean deleted;
    private Date deleteTimestamp;
    private String comments;
    private String qComments;

    @Column(insertable = false) private Date insertTimestamp;
    @Column(insertable = false) private Date updateTimestamp;

}

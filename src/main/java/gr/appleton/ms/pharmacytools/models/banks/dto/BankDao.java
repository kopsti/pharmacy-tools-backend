package gr.appleton.ms.pharmacytools.models.banks.dto;

import gr.appleton.ms.pharmacytools.common.constants.DbConstants;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The model used for mapping DB Bank instances.
 */
@Getter
@Setter
@NamedQuery(name = "BankDao.findByWildcard", query = "select b from BankDao b")
@NamedQuery(name = "BankDao.findByDeleted", query = "select b from BankDao b")
@Entity
@Table(schema = DbConstants.RETMAN_SCHEMA, name = DbConstants.BANKS)
public final class BankDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

}

package gr.appleton.ms.pharmacytools.models.verbals.dto;

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
 * The model used for mapping DB Verbal instances.
 */
@Getter
@Setter
@NamedQuery(name = "VerbalDao.findByWildcard", query = "select v from VerbalDao v")
@Entity
@Table(schema = DbConstants.RETMAN_SCHEMA, name = DbConstants.VERBALS)
public class VerbalDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String key;
    private String verbal;

}

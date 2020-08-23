package gr.appleton.ms.pharmacytools.models.notes.dto;

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
import javax.persistence.Table;

/**
 * The model used for mapping DB Note instances.
 */
@Getter
@Setter
@Entity
@Table(schema = DbConstants.RETMAN_SCHEMA, name = DbConstants.NOTES)
public final class NoteDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = DbConstants.OWNER_ID)
    private UserDao owner;

    private String content;
    private String qContent;
    private boolean important;
    private boolean completed;
    private boolean deleted;
    private Date deleteTimestamp;
    private String comments;
    private String qComments;

    @Column(insertable = false) private Date insertTimestamp;
    @Column(insertable = false) private Date updateTimestamp;

}

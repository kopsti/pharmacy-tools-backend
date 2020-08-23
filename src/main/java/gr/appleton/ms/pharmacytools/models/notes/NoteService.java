package gr.appleton.ms.pharmacytools.models.notes;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import gr.appleton.ms.pharmacytools.authorization.persistence.UserDao;
import gr.appleton.ms.pharmacytools.common.crud.AbstractServiceCrud;
import gr.appleton.ms.pharmacytools.common.crud.CrudService;
import gr.appleton.ms.pharmacytools.common.dto.ModelCommonProperties;
import gr.appleton.ms.pharmacytools.common.exceptions.GenericException;
import gr.appleton.ms.pharmacytools.common.utils.CommonService;
import gr.appleton.ms.pharmacytools.common.utils.GreekLatin;
import gr.appleton.ms.pharmacytools.models.notes.dto.NoteDao;
import gr.appleton.ms.pharmacytools.models.notes.dto.NoteModel;
import gr.appleton.ms.pharmacytools.models.notes.filtering.NotePredicatesBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * The service responsible to handle operations for Note instances management.
 */
@Service
@Slf4j
public final class NoteService extends AbstractServiceCrud<NoteModel, NoteDao, NotePredicatesBuilder>
    implements CrudService<NoteModel, NoteDao> {

    private final NoteRepository notes;

    /**
     * Instantiates a new Note service.
     *
     * @param common the common service
     * @param notes  the note repository
     */
    @Autowired
    public NoteService(final NoteRepository notes, final CommonService common) {
        super(common);
        this.notes = notes;
    }

    @Override
    public CrudRepository<NoteDao, Long> repository() {
        return notes;
    }

    @Override
    public Iterable<NoteDao> queryDaos(final BooleanExpression exp) {
        return notes.findAll(exp != null ? exp : Expressions.asBoolean(true).isTrue());
    }

    @Override
    public NoteModel dao2Model(final NoteDao dao) {
        final NoteModel model = new NoteModel();
        model.setId(dao.getId());
        model.setContent(dao.getContent());
        model.setCompleted(dao.isCompleted());
        model.setImportant(dao.isImportant());

        model.setCommon(new ModelCommonProperties(dao.getInsertTimestamp(), dao.getUpdateTimestamp(), dao.getComments(),
            dao.isDeleted(), dao.getDeleteTimestamp()));
        return model;
    }

    @Override
    public NoteDao model2Dao(final NoteModel model, NoteDao dao, final UserDao userDao) throws GenericException {
        if (dao == null) {
            dao = new NoteDao();
        }

        dao.setContent(model.getContent());
        dao.setQContent(GreekLatin.greek2latin(model.getContent()));
        dao.setImportant(model.isImportant());
        dao.setCompleted(model.isCompleted());
        dao.setOwner(userDao);
        dao.setComments(model.getComments());
        dao.setQComments(GreekLatin.greek2latin(model.getComments()));

        return dao;
    }

    @Override
    public boolean mayEnterBin() {
        return true;
    }

    @Override
    public boolean alreadyInBin(final long id) throws GenericException {
        return retrieveDaoById(id).isDeleted();
    }

    @Override
    public void addInBin(final long id) throws GenericException {
        final NoteDao note = retrieveDaoById(id);
        note.setDeleted(true);
        note.setDeleteTimestamp(new Date());
        repository().save(note);
    }

    @Override
    public Class<NotePredicatesBuilder> getBuilder() {
        return NotePredicatesBuilder.class;
    }

}

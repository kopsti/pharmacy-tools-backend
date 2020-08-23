package gr.appleton.ms.pharmacytools.models.notes;

import com.querydsl.core.types.dsl.BooleanExpression;
import gr.appleton.ms.pharmacytools.models.notes.dto.NoteDao;
import gr.appleton.ms.pharmacytools.models.notes.dto.QNoteDao;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The repository responsible to handle DB operations for Note records management.
 */
@Repository
public interface NoteRepository extends CrudRepository<NoteDao, Long>, QuerydslPredicateExecutor<NoteDao>,
    QuerydslBinderCustomizer<QNoteDao> {

    @Override
    default void customize(final QuerydslBindings bindings, final QNoteDao root) {
        bindings.bind(root.completed).first(BooleanExpression::eq);
        bindings.bind(root.deleted).first(BooleanExpression::eq);
        bindings.bind(root.important).first(BooleanExpression::eq);
    }

}

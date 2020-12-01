package gr.appleton.ms.pharmacytools.models.notes;

import gr.appleton.ms.pharmacytools.models.notes.dto.NoteDao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The repository responsible to handle DB operations for Note records management.
 */
@Repository
public interface NoteRepository extends CrudRepository<NoteDao, Long> {

}

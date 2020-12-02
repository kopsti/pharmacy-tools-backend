package gr.appleton.ms.pharmacytools.models.notes;

import gr.appleton.ms.pharmacytools.common.crud.MyCrudRepository;
import gr.appleton.ms.pharmacytools.models.notes.dto.NoteDao;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * The repository responsible to handle DB operations for Note records management.
 */
@Repository
public interface NoteRepository extends MyCrudRepository<NoteDao, Long> {

    @Override
    Iterable<NoteDao> findByWildcard(@Param("wildcard") String wildcard, boolean deleted);

}

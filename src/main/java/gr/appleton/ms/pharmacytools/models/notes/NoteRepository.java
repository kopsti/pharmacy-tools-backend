package gr.appleton.ms.pharmacytools.models.notes;

import gr.appleton.ms.pharmacytools.common.crud.MyCrudRepository;
import gr.appleton.ms.pharmacytools.models.notes.dto.NoteDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * The repository responsible to handle DB operations for Note records management.
 */
@Repository
public interface NoteRepository extends MyCrudRepository<NoteDao, Long> {

    @Override
    @Query("from NoteDao where "
        + "qContent like concat('%',:wildcard,'%') "
        + "or qComments like concat('%',:wildcard,'%')")
    Iterable<NoteDao> findByWildCard(@Param("wildcard") String wildcard);

}

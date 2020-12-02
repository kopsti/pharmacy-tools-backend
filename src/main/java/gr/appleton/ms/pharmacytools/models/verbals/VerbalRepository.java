package gr.appleton.ms.pharmacytools.models.verbals;

import gr.appleton.ms.pharmacytools.common.crud.MyCrudRepository;
import gr.appleton.ms.pharmacytools.models.verbals.dto.VerbalDao;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The repository responsible to handle DB operations for Verbal records management.
 */
@Repository
public interface VerbalRepository extends MyCrudRepository<VerbalDao, Long> {

    /**
     * Find all Verbal records by key.
     *
     * @param key the key
     * @return the optional
     */
    Optional<VerbalDao> findAllByKey(final String key);

    @Override
    Iterable<VerbalDao> findByWildcard(@Param("wildcard") String wildcard, boolean deleted);

}

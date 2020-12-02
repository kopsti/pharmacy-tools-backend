package gr.appleton.ms.pharmacytools.models.banks;

import gr.appleton.ms.pharmacytools.common.crud.MyCrudRepository;
import gr.appleton.ms.pharmacytools.models.banks.dto.BankDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * The repository responsible to handle DB operations for Bank records management.
 */
@Repository
public interface BankRepository extends MyCrudRepository<BankDao, Long> {

    @Override
    Iterable<BankDao> findByWildcard(@Param("wildcard") String wildcard);

}

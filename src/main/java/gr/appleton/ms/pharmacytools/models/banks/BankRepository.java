package gr.appleton.ms.pharmacytools.models.banks;

import gr.appleton.ms.pharmacytools.models.banks.dto.BankDao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The repository responsible to handle DB operations for Bank records management.
 */
@Repository
public interface BankRepository extends CrudRepository<BankDao, Long> {
}

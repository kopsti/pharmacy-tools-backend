package gr.appleton.ms.pharmacytools.models.accounts;

import gr.appleton.ms.pharmacytools.models.accounts.dto.BankAccountDao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The repository responsible to handle DB operations for Bank Account records management.
 */
@Repository
public interface BankAccountRepository extends CrudRepository<BankAccountDao, Long> {

}

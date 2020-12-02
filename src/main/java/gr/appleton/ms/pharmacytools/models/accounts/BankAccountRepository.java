package gr.appleton.ms.pharmacytools.models.accounts;

import gr.appleton.ms.pharmacytools.common.crud.MyCrudRepository;
import gr.appleton.ms.pharmacytools.models.accounts.dto.BankAccountDao;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * The repository responsible to handle DB operations for Bank Account records management.
 */
@Repository
public interface BankAccountRepository extends MyCrudRepository<BankAccountDao, Long> {

    @Override
    Iterable<BankAccountDao> findByWildcard(@Param("wildcard") String wildcard);

}

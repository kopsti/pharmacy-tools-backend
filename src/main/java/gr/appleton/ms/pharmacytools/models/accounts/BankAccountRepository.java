package gr.appleton.ms.pharmacytools.models.accounts;

import gr.appleton.ms.pharmacytools.models.accounts.dto.BankAccountDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * The repository responsible to handle DB operations for Bank Account records management.
 */
@Repository
public interface BankAccountRepository extends CrudRepository<BankAccountDao, Long> {

    @Query("from BankAccountDao where "
        + "supplier.qTitle like concat('%',:wildcard,'%') "
        + "or supplier.qDescription like concat('%',:wildcard,'%') "
        + "or bank.title like concat('%',:wildcard,'%')")
    Iterable<BankAccountDao> findByWildCard(@Param("wildcard") String wildcard);

}

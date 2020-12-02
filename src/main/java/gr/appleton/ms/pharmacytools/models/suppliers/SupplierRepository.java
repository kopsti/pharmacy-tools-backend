package gr.appleton.ms.pharmacytools.models.suppliers;

import gr.appleton.ms.pharmacytools.models.suppliers.dto.SupplierDao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * The repository responsible to handle DB operations for Supplier records management.
 */
@Repository
public interface SupplierRepository extends CrudRepository<SupplierDao, Long> {

    @Query("from SupplierDao where "
        + "qDescription like concat('%',:wildcard,'%') "
        + "or qTitle like concat('%',:wildcard,'%') "
        + "or email like concat('%',:wildcard,'%') "
        + "or phoneNumber like concat('%',:wildcard,'%') "
        + "or taxAuthority like concat('%',:wildcard,'%') "
        + "or taxId like concat('%',:wildcard,'%') "
        + "or qComments like concat('%',:wildcard,'%')")
    Iterable<SupplierDao> findByWildCard(@Param("wildcard") String wildcard);

}

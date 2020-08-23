package gr.appleton.ms.pharmacytools.authorization.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The interface User repository.
 */
@Repository
public interface UserRepository extends CrudRepository<UserDao, Long> {

    /**
     * Find by username optional.
     *
     * @param email the email
     * @return the optional
     */
    Optional<UserDao> findByEmail(final String email);

}

package gr.appleton.ms.pharmacytools.authorization.persistence;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * The interface Token sec token repository.
 */
public interface TokenSecTokenRepository extends CrudRepository<InitSecTokenDao, Long> {

    /**
     * Find token sec token dao by init token and flow optional.
     *
     * @param token the token
     * @param flow  the flow
     * @return the optional
     */
    Optional<InitSecTokenDao> findTokenSecTokenDaoByInitTokenAndFlow(
        final UUID token, final String flow);

    /**
     * Find token sec token dao by sec token and flow optional.
     *
     * @param token the token
     * @param flow  the flow
     * @return the optional
     */
    Optional<InitSecTokenDao> findTokenSecTokenDaoBySecTokenAndFlow(
        final UUID token, final String flow);
}

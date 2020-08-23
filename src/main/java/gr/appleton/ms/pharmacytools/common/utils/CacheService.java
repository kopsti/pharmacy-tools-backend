package gr.appleton.ms.pharmacytools.common.utils;

import gr.appleton.ms.pharmacytools.authorization.persistence.UserDao;
import gr.appleton.ms.pharmacytools.authorization.persistence.UserRepository;
import gr.appleton.ms.pharmacytools.common.constants.CacheKeys;
import gr.appleton.ms.pharmacytools.common.constants.ExceptionMessages;
import gr.appleton.ms.pharmacytools.common.exceptions.GenericException;
import gr.appleton.ms.pharmacytools.models.verbals.VerbalRepository;
import gr.appleton.ms.pharmacytools.models.verbals.dto.VerbalDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * The Service with cache operations.
 */
@Service
@Slf4j
public class CacheService {

    private static final String VERBALS_CACHE = CacheKeys.VERBALS;
    private static final String USERS_CACHE = CacheKeys.USERS;

    private final UserRepository users;
    private final VerbalRepository verbals;

    /**
     * Instantiates a new Cache Service.
     *
     * @param users   the users
     * @param verbals the verbals
     */
    @Autowired
    public CacheService(final UserRepository users, final VerbalRepository verbals) {
        this.users = users;
        this.verbals = verbals;
    }

    /**
     * Gets user by username.
     *
     * @param username the username
     * @return the user by username
     * @throws GenericException the generic exception
     */
    @Cacheable(USERS_CACHE)
    public UserDao getUserByUsername(final String username) throws GenericException {
        return users.findByEmail(username)
            .orElseThrow(() -> new GenericException(ExceptionMessages.RETRIEVE_BY_ID_FAIL + username));
    }

    /**
     * Gets verbal by key.
     *
     * @param key the key
     * @return the verbal by key
     */
    @Cacheable(CacheKeys.VERBALS)
    public String getVerbalByKey(final String key) {
        log.info("Getting verbal for key {}", key);
        final VerbalDao verbal = verbals.findAllByKey(key).orElse(null);
        if (verbal == null) {
            log.warn("No value found");
            return null;
        } else {
            log.info("Verbal is {}", verbal.getVerbal());
            return verbal.getVerbal();
        }
    }

    /**
     * Clear cache for users.
     */
    @CacheEvict(value = USERS_CACHE, allEntries = true)
    public void clearUsersCache() {
        log.info("Users caches cleared");
    }

    /**
     * Clear cache for verbals.
     */
    @CacheEvict(value = VERBALS_CACHE, allEntries = true)
    public void clearVerbalsCache() {
        log.info("Verbals caches cleared");
    }

    /**
     * Clear all caches.
     */
    @CacheEvict(value = {USERS_CACHE, VERBALS_CACHE}, allEntries = true)
    public void clearAll() {
        log.info("All caches cleared");
    }

}

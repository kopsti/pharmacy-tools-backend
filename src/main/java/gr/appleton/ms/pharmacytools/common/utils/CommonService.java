package gr.appleton.ms.pharmacytools.common.utils;

import gr.appleton.ms.pharmacytools.authorization.persistence.InitSecTokenDao;
import gr.appleton.ms.pharmacytools.authorization.persistence.TokenSecTokenRepository;
import gr.appleton.ms.pharmacytools.authorization.persistence.UserDao;
import gr.appleton.ms.pharmacytools.authorization.persistence.UserRepository;
import gr.appleton.ms.pharmacytools.common.constants.ExceptionMessages;
import gr.appleton.ms.pharmacytools.common.enumerations.SecTokenFlows;
import gr.appleton.ms.pharmacytools.common.exceptions.GenericException;
import gr.appleton.ms.pharmacytools.common.exceptions.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.UUID;

/**
 * The Service with common operations.
 */
@Service
@Slf4j
public final class CommonService {

    private final CacheService cache;
    private final UserRepository users;
    private final TokenSecTokenRepository tokens;

    /**
     * Instantiates a new Common Service.
     *
     * @param cache  the cache service
     * @param users  the users repository
     * @param tokens the Token & Security Token repository
     */
    @Autowired
    public CommonService(final CacheService cache, final UserRepository users, final TokenSecTokenRepository tokens) {
        this.cache = cache;
        this.users = users;
        this.tokens = tokens;
    }

    public void saveTokenSecToken(final InitSecTokenDao initSecTokenDao) {
        tokens.save(initSecTokenDao);
    }

    public InitSecTokenDao getTokensByInitTokenAndFlow(final String initToken, final String flow)
        throws GenericException {
        return tokens.findTokenSecTokenDaoByInitTokenAndFlow(UUID.fromString(initToken), flow)
            .orElseThrow(() -> new GenericException(ExceptionMessages.RETRIEVE_BY_ID_FAIL + initToken + " " + flow));
    }

    public InitSecTokenDao getTokensBySecTokenAndFlow(final String secToken, final String flow)
        throws GenericException {
        return tokens.findTokenSecTokenDaoBySecTokenAndFlow(UUID.fromString(secToken), flow)
            .orElseThrow(() -> new GenericException(ExceptionMessages.RETRIEVE_BY_ID_FAIL + secToken + " " + flow));
    }

    public void validateInitOrSecToken(final boolean isSecToken, final String username, final String token,
                                       final SecTokenFlows flow) throws UnauthorizedException {

        UserDao userDao;
        try {
            userDao = cache.getUserByUsername(username);
        } catch (final GenericException e) {
            throw new UnauthorizedException(ExceptionMessages.GENERIC_UNAUTHORIZED);
        }

        if (isSecToken) {
            validateSecToken(userDao.getEmail(), token, flow);
        } else {
            validateInitToken(userDao.getEmail(), token, flow);
        }
    }

    /**
     * Save user.
     *
     * @param userDao the user dao
     */
    public void saveUser(final UserDao userDao) {
        users.save(userDao);
    }

    /**
     * Gets user by username.
     *
     * @param username the username
     * @return the user by username
     * @throws GenericException the generic exception
     */
    public UserDao getUserByUsername(final String username) throws GenericException {
        return cache.getUserByUsername(username);
    }

    /**
     * Gets verbal by key.
     *
     * @param key the key
     * @return the verbal by key
     */
    public String getVerbalByKey(final String key) {
        return cache.getVerbalByKey(key);
    }

    private void validateInitToken(final String username, final String initToken, final SecTokenFlows flow)
        throws UnauthorizedException {

        InitSecTokenDao initSecTokenDao;

        try {
            initSecTokenDao = getTokensByInitTokenAndFlow(initToken, flow.name());
        } catch (final GenericException e) {
            throw new UnauthorizedException(ExceptionMessages.GENERIC_UNAUTHORIZED);
        }

        try {
            if (!initSecTokenDao.getOwner().getEmail().equalsIgnoreCase(username)
                ||
                initSecTokenDao.getInitTokenExpirationTimestamp().getTime() - Calendar.getInstance().getTime().getTime()
                    < 0) {
                throw new UnauthorizedException(ExceptionMessages.GENERIC_UNAUTHORIZED);
            }
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
            throw new UnauthorizedException(ExceptionMessages.GENERIC_UNAUTHORIZED);
        }
    }

    private void validateSecToken(final String username, final String secToken, final SecTokenFlows flow)
        throws UnauthorizedException {

        InitSecTokenDao initSecTokenDao;

        try {
            initSecTokenDao = getTokensBySecTokenAndFlow(secToken, flow.name());
        } catch (final GenericException e) {
            throw new UnauthorizedException(ExceptionMessages.GENERIC_UNAUTHORIZED);
        }

        try {
            if (!initSecTokenDao.getOwner().getEmail().equalsIgnoreCase(username)
                ||
                initSecTokenDao.getSecTokenExpirationTimestamp().getTime() - Calendar.getInstance().getTime().getTime()
                    < 0) {
                throw new UnauthorizedException(ExceptionMessages.GENERIC_UNAUTHORIZED);
            }
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
            throw new UnauthorizedException(ExceptionMessages.GENERIC_UNAUTHORIZED);
        }
    }

    public String generateInitToken(final String username, final SecTokenFlows flow) throws GenericException {
        final UserDao userDao = cache.getUserByUsername(username);
        final UUID uuid = UUID.randomUUID();
        final Calendar cal = Utilities.getCalendarWithOffset(Calendar.DATE, 3);
        final InitSecTokenDao newInitToken = new InitSecTokenDao();
        newInitToken.setFlow(flow.name());
        newInitToken.setInitToken(uuid);
        newInitToken.setOwner(userDao);
        newInitToken.setInitTokenExpirationTimestamp(cal.getTime());
        saveTokenSecToken(newInitToken);
        return uuid.toString();
    }

    public String generateSecToken(final String initToken, final SecTokenFlows flow) throws GenericException {
        final InitSecTokenDao initSecTokenDao = getTokensByInitTokenAndFlow(initToken, flow.name());
        final UUID secToken = UUID.randomUUID();
        final Calendar cal = Utilities.getCalendarWithOffset(Calendar.MINUTE, 10);
        initSecTokenDao.setSecToken(secToken);
        initSecTokenDao.setSecTokenExpirationTimestamp(cal.getTime());
        saveTokenSecToken(initSecTokenDao);
        return secToken.toString();
    }

    public void expireTokenAndSecToken(final String token, final String secToken, final SecTokenFlows flow)
        throws GenericException {

        final Calendar cal = Utilities.getCalendarWithOffset(Calendar.YEAR, -1);
        InitSecTokenDao initSecTokenDao;
        if (token == null) {
            initSecTokenDao = getTokensBySecTokenAndFlow(secToken, flow.name());
            initSecTokenDao.setSecTokenExpirationTimestamp(cal.getTime());
        } else {
            initSecTokenDao = getTokensByInitTokenAndFlow(token, flow.name());
        }

        initSecTokenDao.setInitTokenExpirationTimestamp(cal.getTime());

        saveTokenSecToken(initSecTokenDao);
    }

}

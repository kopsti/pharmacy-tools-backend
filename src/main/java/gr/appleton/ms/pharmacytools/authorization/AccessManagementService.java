package gr.appleton.ms.pharmacytools.authorization;

import gr.appleton.ms.pharmacytools.authorization.dto.RegistrationRequest;
import gr.appleton.ms.pharmacytools.authorization.enumerations.AuthenticationStatus;
import gr.appleton.ms.pharmacytools.authorization.enumerations.UserEvents;
import gr.appleton.ms.pharmacytools.authorization.persistence.UserDao;
import gr.appleton.ms.pharmacytools.authorization.services.AuthenticationService;
import gr.appleton.ms.pharmacytools.authorization.services.TokenService;
import gr.appleton.ms.pharmacytools.authorization.services.TokenUserDetailsService;
import gr.appleton.ms.pharmacytools.common.constants.ExceptionMessages;
import gr.appleton.ms.pharmacytools.common.enumerations.EmailFlows;
import gr.appleton.ms.pharmacytools.common.enumerations.SecTokenFlows;
import gr.appleton.ms.pharmacytools.common.exceptions.GenericException;
import gr.appleton.ms.pharmacytools.common.exceptions.InactiveUserException;
import gr.appleton.ms.pharmacytools.common.exceptions.UnauthorizedException;
import gr.appleton.ms.pharmacytools.common.utils.CommonService;
import gr.appleton.ms.pharmacytools.common.utils.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * The type Access management service.
 */
@Service
@Slf4j
public class AccessManagementService {

    private final AuthenticationService authenticationService;
    private final TokenService tokenService;
    private final TokenUserDetailsService tokenUserDetailsService;
    private final CommonService common;
    private final PasswordEncoder passwordEncoder;
    private final EmailService email;

    /**
     * Instantiates a new Access management service.
     *
     * @param authenticationService   the authentication service
     * @param tokenService            the token service
     * @param tokenUserDetailsService the token user details service
     * @param common                  the common
     * @param passwordEncoder         the password encoder
     * @param email                   the email
     */
    @Autowired
    public AccessManagementService(final AuthenticationService authenticationService, final TokenService tokenService,
                                   final TokenUserDetailsService tokenUserDetailsService, final CommonService common,
                                   final PasswordEncoder passwordEncoder, final EmailService email) {
        this.authenticationService = authenticationService;
        this.tokenService = tokenService;
        this.tokenUserDetailsService = tokenUserDetailsService;
        this.common = common;
        this.passwordEncoder = passwordEncoder;
        this.email = email;
    }

    /**
     * Authenticate int.
     *
     * @param username the username
     * @param password the password
     * @return the int
     */
    int authenticate(final String username, final String password) {

        int status;

        try {
            authenticationService.authenticate(username, password);
            final UserDao userDao = common.getUserByUsername(username);
            if (!userDao.isActive()) {
                throw new InactiveUserException("User " + username + "is not active");
            }
            status = AuthenticationStatus.OK.getCode();
        } catch (final InactiveUserException e) {
            status = AuthenticationStatus.USER_NOT_ACTIVE.getCode();
        } catch (final UnauthorizedException e) {
            status = AuthenticationStatus.USER_NOT_FOUND.getCode();
        } catch (final Exception e) {
            status = AuthenticationStatus.OTHER_ERROR.getCode();
        }

        return status;
    }

    /**
     * Generate token string.
     *
     * @param email the email
     * @return the string
     * @throws GenericException the generic exception
     */
    String generateToken(final String email) throws GenericException {
        return tokenService.generateToken(tokenUserDetailsService.loadUserByUsername(email));
    }

    /**
     * Register.
     *
     * @param user           the user
     * @param registrarEmail the registrar email
     * @param domain         the domain
     * @param redirectionUrl the redirection url
     * @throws GenericException the generic exception
     */
    void register(final RegistrationRequest user, final String registrarEmail, final String domain,
                  final String redirectionUrl)
        throws GenericException {

        try {
            tokenUserDetailsService.createNewUser(user);
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
            throw new GenericException(ExceptionMessages.GENERIC_ERROR);
        }

        final String uuid = common.generateInitToken(user.getUsername(), SecTokenFlows.ACTIVATE_ACCOUNT);
        email.sendEmailForSecToken(user.getUsername(), SecTokenFlows.ACTIVATE_ACCOUNT, domain, uuid, redirectionUrl);

        email.constructAndSendEmail(EmailFlows.ACCOUNT_ACT_REGISTRAR, registrarEmail, null);
    }

    /**
     * Update user events.
     *
     * @param username the username
     * @param event    the event
     * @throws GenericException the generic exception
     */
    void updateUserEvents(final String username, final UserEvents event) throws GenericException {
        try {
            final UserDao user = common.getUserByUsername(username);

            if (event == UserEvents.LOGIN) {
                user.setLastLoginTimestamp(new Date());
            } else if (event == UserEvents.LOGOUT) {
                user.setLastLogoutTimestamp(new Date());
            } else if (event == UserEvents.PASS_INTERACTION) {
                user.setPassInteractionTimestamp(new Date());
            }
            common.saveUser(user);
        } catch (final Exception e) {
            throw new GenericException("Could not update the user event date");
        }
    }

    /**
     * Change user password.
     *
     * @param username the username
     * @param password the password
     * @throws GenericException the generic exception
     */
    void changeUserPassword(final String username, final String password) throws GenericException {

        final UserDao userDao = common.getUserByUsername(username);

        try {
            userDao.setPass(passwordEncoder.encode(password));
            common.saveUser(userDao);
            updateUserEvents(userDao.getEmail(), UserEvents.PASS_INTERACTION);
            updateUserEvents(userDao.getEmail(), UserEvents.LOGOUT);
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
            throw new GenericException("Could not change password");
        }
    }

    /**
     * Activate account boolean.
     *
     * @param username the username
     * @param token    the sec token
     */
    void activateAccount(final String username, final String token) throws GenericException {

        try {
            common.expireTokenAndSecToken(token, null, SecTokenFlows.ACTIVATE_ACCOUNT);
        } catch (final GenericException e) {
            log.error(e.getMessage(), e);
        }

        UserDao userDao;
        try {
            userDao = common.getUserByUsername(username);
        } catch (final GenericException e) {
            log.error(e.getMessage(), e);
            throw new GenericException("User not found");
        }

        userDao.setActive(true);
        common.saveUser(userDao);
    }

}

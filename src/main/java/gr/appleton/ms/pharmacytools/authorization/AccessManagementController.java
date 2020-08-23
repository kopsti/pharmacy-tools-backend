package gr.appleton.ms.pharmacytools.authorization;

import static gr.appleton.ms.pharmacytools.common.constants.Constants.AGENT;
import static gr.appleton.ms.pharmacytools.common.enumerations.ClientResponses.OK;

import gr.appleton.ms.pharmacytools.authorization.dto.AuthModel;
import gr.appleton.ms.pharmacytools.authorization.dto.ChangePass;
import gr.appleton.ms.pharmacytools.authorization.dto.LoginResponse;
import gr.appleton.ms.pharmacytools.authorization.dto.RegistrationRequest;
import gr.appleton.ms.pharmacytools.authorization.dto.ResetPasswordRequest;
import gr.appleton.ms.pharmacytools.authorization.enumerations.AuthenticationStatus;
import gr.appleton.ms.pharmacytools.authorization.enumerations.UserEvents;
import gr.appleton.ms.pharmacytools.authorization.persistence.UserDao;
import gr.appleton.ms.pharmacytools.common.constants.Endpoints;
import gr.appleton.ms.pharmacytools.common.dto.CommonModel;
import gr.appleton.ms.pharmacytools.common.enumerations.ClientResponses;
import gr.appleton.ms.pharmacytools.common.enumerations.SecTokenFlows;
import gr.appleton.ms.pharmacytools.common.exceptions.GenericException;
import gr.appleton.ms.pharmacytools.common.exceptions.UnauthorizedException;
import gr.appleton.ms.pharmacytools.common.utils.CommonService;
import gr.appleton.ms.pharmacytools.common.utils.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Email;

/**
 * The type Access management controller.
 */
@CrossOrigin
@RestController
@Slf4j
public class AccessManagementController {

    private static final String LOC = "Location";

    private final AccessManagementService access;
    private final CommonService common;
    private final EmailService email;

    /**
     * Instantiates a new Access management controller.
     *
     * @param access the access
     * @param common the common
     * @param email  the email
     */
    @Autowired
    public AccessManagementController(final AccessManagementService access, final CommonService common,
                                      final EmailService email) {
        this.access = access;
        this.common = common;
        this.email = email;
    }

    /**
     * Register response entity.
     *
     * @param registrar   the registrar
     * @param newUserInfo the new user info
     * @param request     the request
     * @return the response entity
     * @throws GenericException the generic exception
     */
    @PostMapping(Endpoints.ACCESS_REGISTER)
    public ResponseEntity<CommonModel> register(@Email @RequestHeader(AGENT) final String registrar,
                                                @Valid @RequestBody final RegistrationRequest newUserInfo,
                                                final HttpServletRequest request) throws GenericException {

        int code = OK.getCode();
        String message = OK.getMessage();
        access.register(newUserInfo, registrar,
            request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort(),
            newUserInfo.getLoginUrl());

        return ResponseEntity.ok(new CommonModel(null, code, message));
    }

    /**
     * Login and get token response entity.
     *
     * @param req the req
     * @return the response entity
     * @throws GenericException the generic exception
     */
    @PostMapping(Endpoints.ACCESS_LOGIN)
    public ResponseEntity<LoginResponse> loginAndGetToken(@Valid @RequestBody final AuthModel req)
        throws GenericException {

        int status = access.authenticate(req.getUsername(), req.getPass());

        String token = null;
        int code = OK.getCode();
        String message = OK.getMessage();
        HttpStatus httpStatus = HttpStatus.OK;

        if (status == AuthenticationStatus.OK.getCode()) {
            token = access.generateToken(req.getUsername());
            access.updateUserEvents(req.getUsername(), UserEvents.LOGIN);
        } else {
            if (status == AuthenticationStatus.USER_NOT_FOUND.getCode()) {
                code = ClientResponses.UNAUTH.getCode();
                message = ClientResponses.UNAUTH.getMessage();
                httpStatus = HttpStatus.UNAUTHORIZED;
            } else if (status == AuthenticationStatus.USER_NOT_ACTIVE.getCode()) {
                code = ClientResponses.INACTIVE_USER.getCode();
                message = ClientResponses.INACTIVE_USER.getMessage();
            } else {
                code = ClientResponses.NOK.getCode();
                message = ClientResponses.NOK.getMessage();
            }
        }

        return ResponseEntity.status(httpStatus)
            .body(new LoginResponse(new CommonModel(token, code, message), req.getUsername(),
                common.getUserByUsername(req.getUsername()).getRole()));
    }

    /**
     * Logout response entity.
     *
     * @param username the username
     * @return the response entity
     * @throws GenericException the generic exception
     */
    @PostMapping(Endpoints.ACCESS_LOGOUT)
    public ResponseEntity<CommonModel> logout(@RequestHeader(AGENT) final String username) throws GenericException {

        access.updateUserEvents(username, UserEvents.LOGOUT);

        return ResponseEntity.ok(new CommonModel(null, OK.getCode(), OK.getMessage()));
    }

    /**
     * User info response entity.
     *
     * @param username the username
     * @return the response entity
     * @throws GenericException the generic exception
     */
    @GetMapping(Endpoints.USER_INFO)
    public ResponseEntity<LoginResponse> userInfo(@RequestHeader(AGENT) final String username) throws GenericException {

        final UserDao user = common.getUserByUsername(username);

        return ResponseEntity.ok().body(
            new LoginResponse(new CommonModel(null, OK.getCode(), OK.getMessage()), user.getEmail(), user.getRole()));
    }

    /**
     * Password reset response entity. //When the user presses the "forgot my password" button. He
     * receives an email There, we send GET request from angular page, passing token & username. this
     * page (component) retrieves these two parameters, sends a request at password/reset together
     * with the new password
     *
     * @param resetPasswordRequest the reset password request
     * @param request              the request
     * @return the response entity
     * @throws GenericException the generic exception
     */
    @PostMapping(Endpoints.PASS_RESET_INIT)
    public ResponseEntity<CommonModel> passwordResetInit(
        @Valid @RequestBody final ResetPasswordRequest resetPasswordRequest, final HttpServletRequest request)
        throws GenericException {

        final String uuid = common.generateInitToken(resetPasswordRequest.getEmail(), SecTokenFlows.RESET_PASS);
        email.sendEmailForSecToken(resetPasswordRequest.getEmail(), SecTokenFlows.RESET_PASS,
            request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort(), uuid,
            resetPasswordRequest.getLoginUrl());

        return ResponseEntity.ok(new CommonModel(null, OK.getCode(), OK.getMessage()));
    }

    /**
     * Password reset response entity.
     *
     * @param username  the username
     * @param initToken the init token
     * @param url       the url
     * @return the response entity
     * @throws GenericException      the generic exception
     * @throws UnauthorizedException the unauthorized exception
     */
    @GetMapping(Endpoints.PASS_RESET_GRANT)
    public ResponseEntity<Void> passwordReset(@Email @RequestParam("i") final String username,
                                              @RequestParam("t") final String initToken,
                                              @RequestParam("n") final String url)
        throws GenericException, UnauthorizedException {

        String secToken;

        common.validateInitOrSecToken(false, username, initToken, SecTokenFlows.RESET_PASS);
        secToken = common.generateSecToken(initToken, SecTokenFlows.RESET_PASS);

        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(LOC, url + "?t=" + secToken);

        return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT).headers(responseHeaders).body(null);
    }

    /**
     * Save password response entity.
     *
     * @param user     the user
     * @param secToken the sec token
     * @param pass     the pass
     * @return the response entity
     * @throws GenericException      the generic exception
     * @throws UnauthorizedException the unauthorized exception
     */
    @PostMapping(value = Endpoints.PASS_SAVE)
    public ResponseEntity<CommonModel> savePassword(@Email @RequestParam("i") final String user,
                                                    @RequestParam("t") final String secToken,
                                                    @Valid @RequestBody final ChangePass pass)
        throws GenericException, UnauthorizedException {

        common.validateInitOrSecToken(true, user, secToken, SecTokenFlows.RESET_PASS);
        access.changeUserPassword(user, pass.getUpdatedPass());
        common.expireTokenAndSecToken(null, secToken, SecTokenFlows.RESET_PASS);

        return ResponseEntity.ok(new CommonModel(null, OK.getCode(), OK.getMessage()));
    }

    /**
     * Password change response entity.
     *
     * @param req the req
     * @return the response entity
     * @throws GenericException the generic exception
     */
    @PostMapping(Endpoints.PASS_CHANGE)
    public ResponseEntity<CommonModel> passwordChange(@Valid @RequestBody final ChangePass req)
        throws GenericException {

        int status = access.authenticate(req.getUsername(), req.getPass());

        int code = OK.getCode();
        String message = OK.getMessage();
        HttpStatus httpStatus = HttpStatus.OK;

        if (status == AuthenticationStatus.OK.getCode()) {
            access.changeUserPassword(req.getUsername(), req.getUpdatedPass());
        } else {
            if (status == AuthenticationStatus.USER_NOT_FOUND.getCode()) {
                code = ClientResponses.UNAUTH.getCode();
                message = ClientResponses.UNAUTH.getMessage();
                httpStatus = HttpStatus.UNAUTHORIZED;
            } else if (status == AuthenticationStatus.USER_NOT_ACTIVE.getCode()) {
                code = ClientResponses.INACTIVE_USER.getCode();
                message = ClientResponses.INACTIVE_USER.getMessage();
            } else {
                code = ClientResponses.NOK.getCode();
                message = ClientResponses.NOK.getMessage();
            }
        }

        return ResponseEntity.status(httpStatus).body(new CommonModel(null, code, message));
    }

    /**
     * Activate user's account after registration.
     *
     * @param username the username
     * @param token    the token
     * @param url      the url
     * @return the response entity
     * @throws GenericException      the generic exception
     * @throws UnauthorizedException the unauthorized exception
     */
    @GetMapping(Endpoints.ACTIVATE_ACCOUNT)
    public ResponseEntity<Void> activateAccountInit(@Email @RequestParam("i") final String username,
                                                    @RequestParam("t") final String token,
                                                    @RequestParam("n") final String url)
        throws GenericException, UnauthorizedException {

        common.validateInitOrSecToken(false, username, token, SecTokenFlows.ACTIVATE_ACCOUNT);
        access.activateAccount(username, token);

        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(LOC, url);

        return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT).headers(responseHeaders).body(null);
    }

}

package gr.appleton.ms.pharmacytools.authorization.services;

import gr.appleton.ms.pharmacytools.common.exceptions.InactiveUserException;
import gr.appleton.ms.pharmacytools.common.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

/**
 * The type Authentication service.
 */
@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;

    /**
     * Instantiates a new Authentication service.
     *
     * @param authenticationManager the authentication manager
     */
    @Autowired
    public AuthenticationService(final AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * Authenticate.
     *
     * @param user     the user
     * @param password the password
     * @throws InactiveUserException the inactive user exception
     * @throws UnauthorizedException the unauthorized exception
     */
    public void authenticate(final String user, final String password)
        throws InactiveUserException, UnauthorizedException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user, password));
        } catch (final DisabledException e) {
            throw new InactiveUserException("This user is not active");
        } catch (final BadCredentialsException e) {
            throw new UnauthorizedException("This user does not exist");
        }
    }
}

package gr.appleton.ms.pharmacytools.authorization.services;

import gr.appleton.ms.pharmacytools.authorization.dto.RegistrationRequest;
import gr.appleton.ms.pharmacytools.authorization.persistence.UserDao;
import gr.appleton.ms.pharmacytools.common.exceptions.GenericException;
import gr.appleton.ms.pharmacytools.common.utils.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * The type Token user details service.
 */
@Service
@Qualifier("tokenUserDetailsService")
@Slf4j
public class TokenUserDetailsService implements UserDetailsService {

    private final CommonService common;
    private final PasswordEncoder passwordEncoder;

    /**
     * Instantiates a new Token user details service.
     *
     * @param common          the common
     * @param passwordEncoder the password encoder
     */
    @Autowired
    public TokenUserDetailsService(final CommonService common, final PasswordEncoder passwordEncoder) {
        this.common = common;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User loadUserByUsername(final String username) {
        final UserDao userDao;
        final String authority;
        try {
            userDao = common.getUserByUsername(username);
            authority = userDao.getRole();
        } catch (final GenericException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }

        return new User(userDao.getEmail(), userDao.getPass(),
            Collections.singleton(() -> authority));
    }

    /**
     * Create new user.
     *
     * @param requestedRegistration the requested registration
     */
    public void createNewUser(final RegistrationRequest requestedRegistration) {
        UserDao newUser = new UserDao();
        newUser.setEmail(requestedRegistration.getUsername());
        newUser.setPass(passwordEncoder.encode(requestedRegistration.getPass()));
        newUser.setRole(requestedRegistration.getRole());
        common.saveUser(newUser);
    }

}

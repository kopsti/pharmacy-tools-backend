package gr.appleton.ms.pharmacytools.authorization.services;

import gr.appleton.ms.pharmacytools.common.exceptions.GenericException;
import gr.appleton.ms.pharmacytools.common.utils.CommonService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * The type Token service.
 */
@Service
@Slf4j
public class TokenService implements Serializable {

    private static final long serialVersionUID = -2550185165626007488L;

    private final long duration;
    private final String keyPrefix;

    @Autowired
    private CommonService common;

    /**
     * Instantiates a new Token service.
     *
     * @param duration  the duration
     * @param keyPrefix the key prefix
     */
    public TokenService(@Value("${jwt.validityPeriod}") final long duration,
                        @Value("${jwt.secretKey}") final String keyPrefix) {
        this.duration = duration;
        this.keyPrefix = keyPrefix;
    }

    /**
     * Generate token string.
     *
     * @param userDetails the user details
     * @return the string
     * @throws GenericException the generic exception
     */
    public String generateToken(final User userDetails) throws GenericException {
        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", userDetails.getAuthorities());

        userDetails.getAuthorities().forEach(aut -> log.info("Authority found {}", aut.getAuthority()));

        return generateToken(claims, userDetails.getUsername());
    }

    /**
     * Validate token boolean.
     *
     * @param token       the token
     * @param key         the key
     * @param userDetails the user details
     * @return the boolean
     */
    public boolean validateToken(final String token, final String key, final UserDetails userDetails) {
        try {
            return (getUsernameFromToken(token, key).equals(userDetails.getUsername()) && !isTokenExpired(token, key));
        } catch (final GenericException e) {
            return false;
        }
    }

    /**
     * Gets username from token.
     *
     * @param token  the token
     * @param keyMid the key mid
     * @return the username from token
     * @throws GenericException the generic exception
     */
    public String getUsernameFromToken(final String token, final String keyMid) throws GenericException {
        return getClaimFromToken(token, generatePersonalSecretKey(keyMid), Claims::getSubject);
    }

    private String generateToken(Map<String, Object> claims, String subject) throws GenericException {
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(subject)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + duration * 1000))
            .signWith(SignatureAlgorithm.HS512, generatePersonalSecretKey(subject))
            .compact();
    }

    private String generatePersonalSecretKey(final String keyMid) throws GenericException {
        final Date lastLoginDate = common.getUserByUsername(keyMid).getLastLogoutTimestamp();
        String key = keyPrefix + keyMid;
        if (lastLoginDate != null) {
            key = key + lastLoginDate.toString();
        }
        return key;
    }

    private boolean isTokenExpired(final String jwt, final String key) throws GenericException {
        return getExpirationDateFromJwt(jwt, key).before(new Date());
    }

    private Date getExpirationDateFromJwt(final String jwt, final String key) throws GenericException {
        return getClaimFromToken(jwt, generatePersonalSecretKey(key), Claims::getExpiration);
    }

    private <T> T getClaimFromToken(final String jwt, final String key, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(getAllClaimsFromJwt(jwt, key));
    }

    private Claims getAllClaimsFromJwt(final String token, final String key) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    }
}

package gr.appleton.ms.pharmacytools.authorization.components;

import gr.appleton.ms.pharmacytools.authorization.services.TokenService;
import gr.appleton.ms.pharmacytools.common.constants.Constants;
import gr.appleton.ms.pharmacytools.common.exceptions.GenericException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The type Jwt request filter.
 */
@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final TokenService tokenService;

    /**
     * Instantiates a new Jwt request filter.
     *
     * @param userDetailsService the user details service
     * @param tokenService       the token service
     */
    @Autowired
    public JwtRequestFilter(@Qualifier("tokenUserDetailsService") final UserDetailsService userDetailsService,
                            final TokenService tokenService) {
        this.userDetailsService = userDetailsService;
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
                                    final FilterChain chain) throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader(Constants.AUTHORIZATION);
        final String keyPrefix = request.getHeader(Constants.AGENT);
        final String tokenPrefix = "Bearer ";

        String username = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith(tokenPrefix)) {
            jwtToken = requestTokenHeader.substring(tokenPrefix.length());
            try {
                username = tokenService.getUsernameFromToken(jwtToken, keyPrefix);
            } catch (final IllegalArgumentException e) {
                log.warn("Unable to get JWT Token");
                log.warn(e.getMessage(), e);
            } catch (final ExpiredJwtException e) {
                log.warn("JWT Token has expired");
                log.warn(e.getMessage(), e);
            } catch (final GenericException e) {
                log.warn("Unexpected error");
                log.error(e.getMessage(), e);
            }
        } else {
            log.warn("Possible issue with the headers");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (tokenService.validateToken(jwtToken, keyPrefix, userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken
                    .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }

}

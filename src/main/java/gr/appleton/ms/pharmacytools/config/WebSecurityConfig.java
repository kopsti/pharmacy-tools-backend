package gr.appleton.ms.pharmacytools.config;

import gr.appleton.ms.pharmacytools.authorization.components.JwtAuthenticationEntryPoint;
import gr.appleton.ms.pharmacytools.authorization.components.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * The Configuration class for Web Security.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String USER = "USER";
    private static final String POWER_USER = "POWER_USER";
    private static final String SYS_ADMIN = "SYS_ADMIN";

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final UserDetailsService userDetailsService;
    private final JwtRequestFilter jwtRequestFilter;
    private final PasswordEncoder passwordEncoder;

    /**
     * Instantiates a new Web security config.
     *
     * @param jwtAuthenticationEntryPoint the jwt authentication entry point
     * @param userDetailsService          the user details service
     * @param jwtRequestFilter            the jwt request filter
     * @param passwordEncoder             the password encoder
     */
    @Autowired
    public WebSecurityConfig(final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                             @Qualifier("tokenUserDetailsService") final UserDetailsService userDetailsService,
                             final JwtRequestFilter jwtRequestFilter, final PasswordEncoder passwordEncoder) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.userDetailsService = userDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Configure global.
     *
     * @param auth the auth
     * @throws Exception the exception
     */
    @Autowired
    public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors()
            .and().csrf().disable()
            // dont authenticate this particular request
            .authorizeRequests()
            .mvcMatchers("/public/**").permitAll()
            .mvcMatchers("/actuator/health").permitAll()
            .mvcMatchers("/actuator/**").hasAnyRole(SYS_ADMIN)
            .mvcMatchers(HttpMethod.POST, "/management/**").hasRole(SYS_ADMIN)
            .mvcMatchers(HttpMethod.PUT, "/management/**").hasRole(SYS_ADMIN)
            .mvcMatchers(HttpMethod.DELETE, "/management/**").hasRole(SYS_ADMIN)
            .mvcMatchers("/access/register").hasAnyRole(POWER_USER, SYS_ADMIN)
            .mvcMatchers("/api/**").hasAnyRole(USER, POWER_USER)
            // all other requests need to be validated
            .anyRequest().authenticated()
            // make sure we use stateless session; session won't be used to
            // store user's state.
            .and().exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // Add a filter to validate the tokens with every request
        httpSecurity.headers().cacheControl().and().frameOptions();
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

}

package com.example.pranshee.config;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import com.example.pranshee.exceptionhandling.CustomAccessDeniedHandler;
import com.example.pranshee.exceptionhandling.CustomBasicAuthenticationEntryPoint;

@Profile("prod") // This annotation indicates that this configuration will be used only in the
                 // production profile, and it will be ignored in other profiles like default or
                 // dev
@Configuration
public class ProjectSecurityProdConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        // http.authorizeHttpRequests((requests) -> requests.anyRequest().denyAll());
        // http.authorizeHttpRequests((requests) -> requests.anyRequest().permitAll());
        http.redirectToHttps(withDefaults())
                // rejects http requests and accepts only https
                // requests
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                        (requests) -> requests.requestMatchers("/myAccount", "/myBalance", "/myCards").authenticated()
                                .requestMatchers("/myNotice", "/myContact", "/error", "/register", "/invalidSession")
                                .permitAll());
        // http.formLogin(httpSecurityFormLoginConfrigurer->
        // httpSecurityFormLoginConfrigurer.disable());
        // http.httpBasic(httpBasicConfig -> httpBasicConfig.disable());
        http.formLogin(withDefaults());
        // http.httpBasic(withDefaults());
        // We have customized the basic authentication entry point to send a custom
        // error message in the
        // response header when the
        // user is not authorized. So we need to use that here.
        http.httpBasic(
                httpBasicConfig -> httpBasicConfig.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));

        // all these exception are handling for 401 unauthorized error and we can set
        // global exception handling for all the authentication entry points in the
        // application
        // http.exceptionHandling(httpBasicConfig ->
        // httpBasicConfig.authenticationEntryPoint(new
        // CustomBasicAuthenticationEntryPoint()));
        // this is the another approach to set the custom authentication entry point
        // for basic authentication
        // this approach has the advantage that we can set global exception handling for
        // all the
        // authentication entry points in the application
        // and we can also set different authentication entry points for
        // different authentication mechanisms in the application
        /*
         * http.exceptionHandling(httpBasicConfig -> httpBasicConfig
         * .authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
         */

        http.exceptionHandling(ehc -> ehc.accessDeniedHandler(new CustomAccessDeniedHandler()));

        /*
         * this is for the invalid session handling after session timeout and
         * it will redirect the user to the login page when the session is invalid and
         * it will also send a custom error message in the response header when
         * the session is invalid and it will also send a 401 unauthorized status
         * code in the response.
         * mention "/invalidSession" in the permitAll() method above to allow
         * access to this endpoint without authentication and you have to provide a real
         * page this ia an
         * example of how to handle invalid session and you can customize it as per your
         * requirement
         */
        http.sessionManagement(sessionManagementConfig -> sessionManagementConfig
                .invalidSessionUrl("invalidSession").maximumSessions(3).maxSessionsPreventsLogin(true));
        return http.build();
    }

    /**
     * @author vipin
     *         <p>
     *         {noop} is used to indicate that the password is stored in plain text.
     *         </p>
     * @return
     */
    // @Bean
    // public UserDetailsService userDetailsService(DataSource dataSource) {
    // // UserDetails user = User.withUsername("user")
    // // .password("{noop}AsthaT@12345").authorities("read").build();
    // // UserDetails admin = User.withUsername("admin")
    // //
    // .password("{bcrypt}$2a$12$HoKwiicDiWJLtWPK6uxLrupemnEbzl9VAXqDNGgRCn4J1PnjilXmC").authorities("admin")
    // // .build();

    // return new JdbcUserDetailsManager(dataSource);

    // }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * * Bean to check if the password is compromised using HaveIBeenPwned API
     * 
     * @return CompromisedPasswordChecker
     */
    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }

}

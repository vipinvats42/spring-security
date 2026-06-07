package com.example.pranshee.config;

import static org.springframework.security.config.Customizer.withDefaults;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

@Configuration
public class ProjectSecurityConfig {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		// http.authorizeHttpRequests((requests) -> requests.anyRequest().denyAll());
		// http.authorizeHttpRequests((requests) -> requests.anyRequest().permitAll());
		http.authorizeHttpRequests(
				(requests) -> requests.requestMatchers("/myAccount", "/myBalance", "/myCards").authenticated()
						.requestMatchers("/myNotice", "/myContact", "/error").permitAll());
		// http.formLogin(httpSecurityFormLoginConfrigurer->
		// httpSecurityFormLoginConfrigurer.disable());
		// http.httpBasic(httpBasicConfig -> httpBasicConfig.disable());
		http.formLogin(withDefaults());
		http.httpBasic(withDefaults());
		return http.build();
	}

	/**
	 * @author vipin
	 *         <p>
	 *         {noop} is used to indicate that the password is stored in plain text.
	 *         </p>
	 * @return
	 */
	@Bean
	public UserDetailsService userDetailsService(DataSource dataSource) {
		// UserDetails user = User.withUsername("user")
		// .password("{noop}AsthaT@12345").authorities("read").build();
		// UserDetails admin = User.withUsername("admin")
		// .password("{bcrypt}$2a$12$HoKwiicDiWJLtWPK6uxLrupemnEbzl9VAXqDNGgRCn4J1PnjilXmC").authorities("admin")
		// .build();

		return new JdbcUserDetailsManager(dataSource);

	}

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

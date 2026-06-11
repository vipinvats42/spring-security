package com.example.pranshee.config;

import org.jspecify.annotations.Nullable;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

//this class has custom authentication provider which will be used to authenticate the user with username 
// and password by default spring security provides DaoAuthenticationProvider which will be used to 
//authenticate the user with username and password by default but we can create our own custom 
//authentication provider by implementing AuthenticationProvider interface and overriding the 
//authenticate method to provide our own authentication logic and also overriding the supports 
//method to specify which type of authentication this provider supports

@RequiredArgsConstructor
@Component
@Profile("!prod") // This annotation indicates that this authentication provider will be used in
                  // all profiles except prod, and it will be ignored in the production profile
public class ProjectUseramePwsAutheticatioProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public @Nullable Authentication authenticate(Authentication arg0) throws AuthenticationException {
        String username = arg0.getName();
        String password = arg0.getCredentials().toString();

        System.out.println("User Details.... " + userDetailsService.loadUserByUsername(username));

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

}
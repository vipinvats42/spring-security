package com.example.pranshee.events;

import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/*
@Slf4j is used for log the events in the application. It is a Lombok annotation that provides a logger
 instance to the class. This allows for easy logging of messages, 
errors, and other information during the execution of the application.  

EventListener is a Spring annotation that marks a method as an event listener.
without this not listening to the events. It allows the method to be automatically
 invoked when a specific event occurs in the application context. 
*/

@Component
@Slf4j
public class AuthenticationEvents {

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent successEvent) {
        log.info("login success for the user : {}", successEvent.getAuthentication().getName());
    }

    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent failureEvent) {
        log.error("login failure for the user : {}", failureEvent.getAuthentication().getName(),
                failureEvent.getException().getMessage());
    }

}

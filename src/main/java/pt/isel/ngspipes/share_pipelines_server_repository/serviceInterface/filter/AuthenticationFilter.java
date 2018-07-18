package pt.isel.ngspipes.share_pipelines_server_repository.serviceInterface.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import pt.isel.ngspipes.share_core.logic.service.AuthenticationService;

import javax.naming.AuthenticationException;

@Component
public class AuthenticationFilter {

    @Component
    public static class AuthenticationEventListener {

        private final Logger LOGGER = LoggerFactory.getLogger(AuthenticationEventListener.class);



        @Autowired
        private AuthenticationService authenticationService;



        @EventListener
        public void authenticationFailed(AuthenticationFailureBadCredentialsEvent e) throws AuthenticationException {
            WebAuthenticationDetails auth = (WebAuthenticationDetails) e.getAuthentication().getDetails();

            String ip = auth.getRemoteAddress();
            if(authenticationService.isBlocked(ip)) {
                LOGGER.warn("IP:" + auth.getRemoteAddress() + " Blocked!");
                throw new AuthenticationException("This user is blocked!");
            }
        }

    }

    @Component
    public static class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

        private final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFailureListener.class);



        @Autowired
        private AuthenticationService authenticationService;



        public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent e) {
            WebAuthenticationDetails auth = (WebAuthenticationDetails) e.getAuthentication().getDetails();

            LOGGER.warn("IP:" + auth.getRemoteAddress() + " Failed to Login!");

            authenticationService.authenticationFailed(auth.getRemoteAddress());
        }
    }

    @Component
    public static class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

        @Autowired
        private AuthenticationService authenticationService;



        public void onApplicationEvent(AuthenticationSuccessEvent e) {
            WebAuthenticationDetails auth = (WebAuthenticationDetails) e.getAuthentication().getDetails();

            authenticationService.authenticationSucceeded(auth.getRemoteAddress());
        }
    }

}

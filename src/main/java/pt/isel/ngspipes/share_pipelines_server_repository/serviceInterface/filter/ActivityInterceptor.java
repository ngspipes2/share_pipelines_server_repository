package pt.isel.ngspipes.share_pipelines_server_repository.serviceInterface.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.handler.MappedInterceptor;
import pt.isel.ngspipes.share_core.logic.domain.User;
import pt.isel.ngspipes.share_core.logic.service.ICurrentUserSupplier;
import pt.isel.ngspipes.share_core.logic.service.IService;
import pt.isel.ngspipes.share_core.logic.service.exceptions.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin
@Component
public class ActivityInterceptor extends HandlerInterceptorAdapter {

    private static final String UNAUTHENTICATED_USER_NAME = "UnauthenticatedUser";
    private static final String REQUEST_MSG = "REQUEST -> %s %s %s";
    private static final String RESPONSE_MSG = "RESPONSE -> %s %s %s %s";



    private final Logger LOGGER = LoggerFactory.getLogger(ActivityInterceptor.class);



    @Autowired
    private IService<User, String> userService;
    @Autowired
    private ICurrentUserSupplier currentUserSupplier;



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logRequestReceived(request);
        return true;
    }

    private void logRequestReceived(HttpServletRequest request) throws ServiceException {
        User currentUser = currentUserSupplier.get();

        String userName = (currentUser == null) ? UNAUTHENTICATED_USER_NAME : currentUser.getUserName();
        String url = request.getRequestURI();
        String method = request.getMethod();

        LOGGER.info(String.format(REQUEST_MSG, userName, method, url));
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logRequestAnswered(request, response);
    }

    private void logRequestAnswered(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        User currentUser = currentUserSupplier.get();

        String userName = (currentUser == null) ? UNAUTHENTICATED_USER_NAME : currentUser.getUserName();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        int status = response.getStatus();

        LOGGER.info(String.format(RESPONSE_MSG, userName, method, uri, status));
    }

    @Bean
    private MappedInterceptor getActivityInterceptor() {
        return new MappedInterceptor(null, this);
    }

}
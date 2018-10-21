package pt.isel.ngspipes.share_pipelines_server_repository.serviceInterface.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import pt.isel.ngspipes.share_core.logic.domain.AccessToken;
import pt.isel.ngspipes.share_core.logic.domain.User;
import pt.isel.ngspipes.share_core.logic.service.accessToken.IAccessTokenService;
import pt.isel.ngspipes.share_core.logic.service.exceptions.ServiceException;
import pt.isel.ngspipes.share_core.logic.service.user.IUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

@CrossOrigin
@Component
public class AuthenticationFilter extends HandlerInterceptorAdapter {

    @Autowired
    private IUserService userService;
    @Autowired
    private IAccessTokenService accessTokenService;
    @Autowired
    private PasswordEncoder passwordEncoder;



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String header = request.getHeader("Authorization");

        if(header != null) {
            if(header.startsWith("Basic"))
                validateAsBasicAuthentication(header);
            else if(header.startsWith("Bearer"))
                validateAsTokenAuthentication(header);
            else
                throw new ServiceException("Unknown Authorization header format!");
        }

        return true;
    }

    private void validateAsBasicAuthentication(String header) throws ServiceException {
        header = header.replace("Basic", "");
        header = new String(Base64.getDecoder().decode(header));

        if(!header.contains(":"))
            throw new BadCredentialsException("Invalid credentials!");

        String userName = header.split(":")[0];
        String password = header.split(":")[1];

        User user = userService.getById(userName);

        if(user == null)
            throw new BadCredentialsException("Invalid credentials!");

        if(!passwordEncoder.matches(password, user.getPassword()))
            throw new BadCredentialsException("Invalid credentials!");
    }

    private void validateAsTokenAuthentication(String header) throws ServiceException {
        header = header.replace("Bearer", "");

        AccessToken token = accessTokenService.getAccessTokenByToken(header);

        if(token == null)
            throw new BadCredentialsException("Invalid credentials!");
    }

}

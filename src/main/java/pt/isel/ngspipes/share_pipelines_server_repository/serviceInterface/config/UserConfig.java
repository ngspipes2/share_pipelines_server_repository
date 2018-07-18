package pt.isel.ngspipes.share_pipelines_server_repository.serviceInterface.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import pt.isel.ngspipes.share_core.logic.domain.User;
import pt.isel.ngspipes.share_core.logic.service.ICurrentUserSupplier;
import pt.isel.ngspipes.share_core.logic.service.IService;

@Configuration
public class UserConfig {

    @Autowired
    private IService<User, String> userService;



    @Bean
    public ICurrentUserSupplier getCurrentUserSupplier() {
        return () -> {
            SecurityContext context = SecurityContextHolder.getContext();
            Authentication auth = context.getAuthentication();

            if(auth == null)
                return null;

            return userService.getById(auth.getName());
        };
    }

}

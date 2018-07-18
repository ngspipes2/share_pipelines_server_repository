package pt.isel.ngspipes.share_pipelines_server_repository.serviceInterface.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import pt.isel.ngspipes.share_core.logic.domain.User;
import pt.isel.ngspipes.share_core.logic.service.IService;
import pt.isel.ngspipes.share_core.logic.service.exceptions.ServiceException;
import pt.isel.ngspipes.share_pipelines_server_repository.serviceInterface.config.Routes;

import java.util.Collection;
import java.util.Properties;

@CrossOrigin
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private IService<User, String> userService;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()

            .antMatchers(HttpMethod.OPTIONS).permitAll()

            .antMatchers(HttpMethod.GET, Routes.GET_ALL_PIPELINES_REPOSITORIES).hasAnyRole(User.Role.NORMAL.toString(), User.Role.ADMIN.toString())
            .antMatchers(Routes.GET_PIPELINES_REPOSITORY).hasAnyRole(User.Role.NORMAL.toString(), User.Role.ADMIN.toString())
            .antMatchers(HttpMethod.POST, Routes.INSERT_PIPELINES_REPOSITORY).hasAnyRole(User.Role.NORMAL.toString(), User.Role.ADMIN.toString())
            .antMatchers(Routes.UPDATE_PIPELINES_REPOSITORY).hasAnyRole(User.Role.NORMAL.toString(), User.Role.ADMIN.toString())
            .antMatchers(Routes.DELETE_PIPELINES_REPOSITORY).hasAnyRole(User.Role.NORMAL.toString(), User.Role.ADMIN.toString())

            .and().httpBasic()
            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(inMemoryUserDetailsManager())
            .passwordEncoder(passwordEncoder);
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() throws ServiceException {
        Collection<User> users = userService.getAll();

        Properties properties = new Properties();

        for(User user : users)
            properties.put(user.getUserName(), user.getPassword()+",ROLE_"+user.getRole().toString()+",enabled");

        return new InMemoryUserDetailsManager(properties);
    }

}

package pt.isel.ngspipes.share_pipelines_server_repository.serviceInterface.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import pt.isel.ngspipes.share_authentication_server.logic.domain.User;
import pt.isel.ngspipes.share_pipelines_server_repository.serviceInterface.config.Routes;

import javax.sql.DataSource;

@CrossOrigin
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DataSource dataSource;



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
            .antMatchers(Routes.GET_PIPELINES_REPOSITORIES_OF_USER).hasAnyRole(User.Role.NORMAL.toString(), User.Role.ADMIN.toString())

            .antMatchers(HttpMethod.GET, Routes.GET_ALL_USER_MEMBERS).hasAnyRole(User.Role.NORMAL.toString(), User.Role.ADMIN.toString())
            .antMatchers(Routes.GET_USER_MEMBER).hasAnyRole(User.Role.NORMAL.toString(), User.Role.ADMIN.toString())
            .antMatchers(HttpMethod.POST, Routes.INSERT_USER_MEMBER).hasAnyRole(User.Role.NORMAL.toString(), User.Role.ADMIN.toString())
            .antMatchers(Routes.UPDATE_USER_MEMBER).hasAnyRole(User.Role.NORMAL.toString(), User.Role.ADMIN.toString())
            .antMatchers(Routes.DELETE_USER_MEMBER).hasAnyRole(User.Role.NORMAL.toString(), User.Role.ADMIN.toString())
            .antMatchers(Routes.GET_USER_MEMBERS_WITH_USER).hasAnyRole(User.Role.NORMAL.toString(), User.Role.ADMIN.toString())
            .antMatchers(Routes.GET_USER_MEMBERS_OF_REPOSITORY).hasAnyRole(User.Role.NORMAL.toString(), User.Role.ADMIN.toString())

            .antMatchers(HttpMethod.GET, Routes.GET_ALL_GROUP_MEMBERS).hasAnyRole(User.Role.NORMAL.toString(), User.Role.ADMIN.toString())
            .antMatchers(Routes.GET_GROUP_MEMBER).hasAnyRole(User.Role.NORMAL.toString(), User.Role.ADMIN.toString())
            .antMatchers(HttpMethod.POST, Routes.INSERT_GROUP_MEMBER).hasAnyRole(User.Role.NORMAL.toString(), User.Role.ADMIN.toString())
            .antMatchers(Routes.UPDATE_GROUP_MEMBER).hasAnyRole(User.Role.NORMAL.toString(), User.Role.ADMIN.toString())
            .antMatchers(Routes.DELETE_GROUP_MEMBER).hasAnyRole(User.Role.NORMAL.toString(), User.Role.ADMIN.toString())
            .antMatchers(Routes.GET_GROUP_MEMBERS_WITH_GROUP).hasAnyRole(User.Role.NORMAL.toString(), User.Role.ADMIN.toString())
            .antMatchers(Routes.GET_GROUP_MEMBERS_OF_REPOSITORY).hasAnyRole(User.Role.NORMAL.toString(), User.Role.ADMIN.toString())

            .antMatchers(HttpMethod.GET, Routes.GET_PIPELINES_REPOSITORY_IMAGE).permitAll()
            .antMatchers(HttpMethod.POST, Routes.UPDATE_PIPELINES_REPOSITORY_IMAGE).hasAnyRole(User.Role.NORMAL.toString(), User.Role.ADMIN.toString())
            .antMatchers(HttpMethod.DELETE, Routes.DELETE_PIPELINES_REPOSITORY_IMAGE).hasAnyRole(User.Role.NORMAL.toString(), User.Role.ADMIN.toString())

            .antMatchers(Routes.PIPELINES_REPOSITORY_SERVER_URI).permitAll()

            .and().httpBasic()
            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
            .dataSource(dataSource)
            .usersByUsernameQuery("select username, password, true from _user where username=?")
            .authoritiesByUsernameQuery("select username, concat('ROLE_', role) from _user where username=?")
            .passwordEncoder(passwordEncoder);
    }

}

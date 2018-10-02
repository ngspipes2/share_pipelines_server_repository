package pt.isel.ngspipes.share_pipelines_server_repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import pt.isel.ngspipes.share_authentication_server.logic.service.PermissionService;

@ComponentScan(
    value = {
        "pt.isel.ngspipes.share_core",
        "pt.isel.ngspipes.share_authentication_server",
        "pt.isel.ngspipes.share_pipelines_server_repository"
    },
    excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = pt.isel.ngspipes.share_authentication_server.App.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = PermissionService.class),
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = "pt.isel.ngspipes.share_authentication_server.serviceInterface.controller.*"),
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = "pt.isel.ngspipes.share_authentication_server.serviceInterface.filters.*")
    }
)
@SpringBootApplication
public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);



    public static void main(String[] args) {
        LOGGER.info("::STARTING::");

        SpringApplication.run(App.class, args);

        LOGGER.info("::RUNNING::");
    }

}

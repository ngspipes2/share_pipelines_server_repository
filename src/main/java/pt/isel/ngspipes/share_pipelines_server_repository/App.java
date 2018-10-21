package pt.isel.ngspipes.share_pipelines_server_repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(
    {
        "pt.isel.ngspipes"
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

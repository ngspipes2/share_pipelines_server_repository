package pt.isel.ngspipes.share_pipelines_server_repository.serviceInterface.config;

import org.kohsuke.github.GitHub;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pt.isel.ngspipes.dsl_core.descriptors.utils.GithubAPI;

import java.io.IOException;

@Configuration
public class GithubConfig {

    @Value("${github.user}")
    private String githubUser;

    @Value("${github.token}")
    private String githubToken;



    @Bean
    public GitHub createGitHub() throws IOException {
        return GithubAPI.getGitHub(githubUser, null, null, githubToken);
    }

}

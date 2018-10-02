package pt.isel.ngspipes.share_pipelines_server_repository.logic.service.repositoryService;

import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.isel.ngspipes.dsl_core.descriptors.pipeline.repository.GithubPipelinesRepository;
import pt.isel.ngspipes.dsl_core.descriptors.utils.GithubAPI;
import pt.isel.ngspipes.pipeline_repository.IPipelinesRepository;
import pt.isel.ngspipes.share_core.logic.service.exceptions.ServiceException;
import pt.isel.ngspipes.share_pipelines_server_repository.logic.domain.PipelinesRepositoryMetadata;
import pt.isel.ngspipes.share_pipelines_server_repository.logic.service.repositoryMetadata.IPipelinesRepositoryMetadataService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class PipelinesRepositoryService implements IPipelinesRepositoryService {

    @Value("${github.user}")
    private String githubUser;
    @Value("${github.token}")
    private String githubToken;

    @Autowired
    private GitHub github;

    @Autowired
    private IPipelinesRepositoryMetadataService pipelinesRepositoryService;



    @Override
    @Transactional
    public void createRepository(PipelinesRepositoryMetadata repository) throws ServiceException {
        pipelinesRepositoryService.insert(repository);

        try {
            github.createRepository(getRepositoryName(repository.getId())).create();
        } catch (IOException e) {
            throw new ServiceException("Error creating repository!", e);
        }
    }

    @Override
    @Transactional
    public void deleteRepository(int id) throws ServiceException {
        pipelinesRepositoryService.delete(id);

        try {
            String repo = githubUser + "/" + getRepositoryName(id);
            GHRepository repository = GithubAPI.getGHRepository(github, repo);
            repository.delete();
        } catch (IOException e) {
            throw new ServiceException("Error deleting repository!", e);
        }
    }

    @Override
    public IPipelinesRepository getRepository(int id) throws ServiceException {
        String location = githubUser + "/" + getRepositoryName(id);

        Map<String, Object> config = new HashMap<>();
        config.put(GithubPipelinesRepository.USER_NAME_CONFIG_KEY, githubUser);
        config.put(GithubPipelinesRepository.ACCESS_TOKEN_CONFIG_KEY, githubToken);

        return new GithubPipelinesRepository(location, config);
    }

    private String getRepositoryName(int repositoryId) {
        return "Pipelines_" + repositoryId;
    }

}

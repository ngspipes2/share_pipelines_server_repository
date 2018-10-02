package pt.isel.ngspipes.share_pipelines_server_repository.serviceInterface.controller.facade;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.isel.ngspipes.share_pipelines_server_repository.logic.domain.PipelinesRepositoryMetadata;
import pt.isel.ngspipes.share_pipelines_server_repository.serviceInterface.config.Routes;

import java.util.Collection;

@CrossOrigin
@RestController
public interface IPipelinesRepositoryController {

    @RequestMapping(value = Routes.GET_ALL_PIPELINES_REPOSITORIES, method = RequestMethod.GET)
    ResponseEntity<Collection<PipelinesRepositoryMetadata>> getAllRepositories() throws Exception;

    @RequestMapping(value = Routes.GET_PIPELINES_REPOSITORY, method = RequestMethod.GET)
    ResponseEntity<PipelinesRepositoryMetadata> getRepository(@PathVariable int repositoryId) throws Exception;

    @RequestMapping(value = Routes.INSERT_PIPELINES_REPOSITORY, method = RequestMethod.POST)
    ResponseEntity<Integer> insertRepository(@RequestBody PipelinesRepositoryMetadata repository) throws Exception;

    @RequestMapping(value = Routes.UPDATE_PIPELINES_REPOSITORY, method = RequestMethod.PUT)
    ResponseEntity<Void> updateRepository(@RequestBody PipelinesRepositoryMetadata repository, @PathVariable int repositoryId) throws Exception;

    @RequestMapping(value = Routes.DELETE_PIPELINES_REPOSITORY, method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteRepository(@PathVariable int repositoryId) throws Exception;

    @RequestMapping(value = Routes.GET_PIPELINES_REPOSITORIES_OF_USER, method = RequestMethod.GET)
    ResponseEntity<Collection<PipelinesRepositoryMetadata>> getPipelinesRepositoriesOfUser(@PathVariable String userName) throws Exception;

}

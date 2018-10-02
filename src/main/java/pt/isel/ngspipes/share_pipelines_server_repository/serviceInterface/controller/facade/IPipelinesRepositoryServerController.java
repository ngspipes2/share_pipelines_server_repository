package pt.isel.ngspipes.share_pipelines_server_repository.serviceInterface.controller.facade;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.isel.ngspipes.dsl_core.descriptors.pipeline.jackson_entities.typed.TypedPipelineDescriptor;
import pt.isel.ngspipes.share_pipelines_server_repository.serviceInterface.config.Routes;

import java.util.Collection;

@CrossOrigin
@RestController
public interface IPipelinesRepositoryServerController {

    @RequestMapping(value = Routes.GET_ALL_PIPELINES_REPOSITORY_SERVER_URI, method = RequestMethod.GET)
    ResponseEntity<Collection<TypedPipelineDescriptor>> getAll(@PathVariable int repositoryId, @RequestHeader(value = "Authorization", required = false) String authHeader) throws Exception;

    @RequestMapping(value = Routes.GET_PIPELINES_REPOSITORY_SERVER_URI, method = RequestMethod.GET)
    ResponseEntity<TypedPipelineDescriptor> get(@PathVariable int repositoryId, @PathVariable String pipelineName, @RequestHeader(value = "Authorization", required = false) String authHeader) throws Exception;

    @RequestMapping(value = Routes.INSERT_PIPELINES_REPOSITORY_SERVER_URI, method = RequestMethod.POST)
    ResponseEntity<Void> insert(@PathVariable int repositoryId, @RequestBody TypedPipelineDescriptor pipeline, @RequestHeader(value = "Authorization", required = false) String authHeader) throws Exception;

    @RequestMapping(value = Routes.UPDATE_PIPELINES_REPOSITORY_SERVER_URI, method = RequestMethod.PUT)
    ResponseEntity<Void> update(@PathVariable int repositoryId, @RequestBody TypedPipelineDescriptor pipeline, @RequestHeader(value = "Authorization", required = false) String authHeader) throws Exception;

    @RequestMapping(value = Routes.DELETE_PIPELINES_REPOSITORY_SERVER_URI, method = RequestMethod.DELETE)
    ResponseEntity<Void> delete(@PathVariable int repositoryId, @PathVariable String pipelineName, @RequestHeader(value = "Authorization", required = false) String authHeader) throws Exception;

}

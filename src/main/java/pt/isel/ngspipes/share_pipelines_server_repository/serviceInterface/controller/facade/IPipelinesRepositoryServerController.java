package pt.isel.ngspipes.share_pipelines_server_repository.serviceInterface.controller.facade;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.isel.ngspipes.pipeline_descriptor.IPipelineDescriptor;
import pt.isel.ngspipes.share_pipelines_server_repository.serviceInterface.config.Routes;

import java.util.Collection;

@CrossOrigin
@RestController
public interface IPipelinesRepositoryServerController {

    @RequestMapping(value = Routes.GET_LOGO_URI, method = RequestMethod.GET)
    ResponseEntity<byte[]> getLogo(@PathVariable int repositoryId, @RequestHeader(value = "Authorization", required = false) String authHeader) throws Exception;

    @RequestMapping(value = Routes.SET_LOGO_URI, method = RequestMethod.POST)
    ResponseEntity<Void> setLogo(@PathVariable int repositoryId, @RequestHeader(value = "Authorization", required = false) String authHeader, @RequestBody(required = false) byte[] logo) throws Exception;

    @RequestMapping(value = Routes.GET_ALL_PIPELINES_URI, method = RequestMethod.GET)
    ResponseEntity<Collection<IPipelineDescriptor>> getAll(@PathVariable int repositoryId, @RequestHeader(value = "Authorization", required = false) String authHeader) throws Exception;

    @RequestMapping(value = Routes.GET_PIPELINE_URI, method = RequestMethod.GET)
    ResponseEntity<IPipelineDescriptor> get(@PathVariable int repositoryId, @PathVariable String pipelineName, @RequestHeader(value = "Authorization", required = false) String authHeader) throws Exception;

    @RequestMapping(value = Routes.INSERT_PIPELINE_URI, method = RequestMethod.POST)
    ResponseEntity<Void> insert(@PathVariable int repositoryId, @RequestBody IPipelineDescriptor pipeline, @RequestHeader(value = "Authorization", required = false) String authHeader) throws Exception;

    @RequestMapping(value = Routes.UPDATE_PIPELINE_URI, method = RequestMethod.PUT)
    ResponseEntity<Void> update(@PathVariable int repositoryId, @RequestBody IPipelineDescriptor pipeline, @RequestHeader(value = "Authorization", required = false) String authHeader) throws Exception;

    @RequestMapping(value = Routes.DELETE_PIPELINE_URI, method = RequestMethod.DELETE)
    ResponseEntity<Void> delete(@PathVariable int repositoryId, @PathVariable String pipelineName, @RequestHeader(value = "Authorization", required = false) String authHeader) throws Exception;

}

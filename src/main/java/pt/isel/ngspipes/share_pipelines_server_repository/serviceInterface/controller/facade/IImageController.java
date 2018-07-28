package pt.isel.ngspipes.share_pipelines_server_repository.serviceInterface.controller.facade;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pt.isel.ngspipes.share_pipelines_server_repository.serviceInterface.config.Routes;

import javax.servlet.http.HttpServletResponse;

@CrossOrigin
@RestController
public interface IImageController {

    @RequestMapping(value = Routes.GET_PIPELINES_REPOSITORY_IMAGE, method = RequestMethod.GET)
    ResponseEntity<Void> getPipelinesRepositoryImage(HttpServletResponse response, @PathVariable String repositoryId) throws Exception;

    @RequestMapping(value = Routes.UPDATE_PIPELINES_REPOSITORY_IMAGE, method = RequestMethod.POST)
    ResponseEntity<Void> updatePipelinesRepositoryImage(@RequestPart(value = "file") MultipartFile file, @PathVariable String repositoryId) throws Exception;

    @RequestMapping(value = Routes.DELETE_PIPELINES_REPOSITORY_IMAGE, method = RequestMethod.DELETE)
    ResponseEntity<Void> deletePipelinesRepositoryImage(@PathVariable String repositoryId) throws Exception;

}

package pt.isel.ngspipes.share_pipelines_server_repository.serviceInterface.controller.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pt.isel.ngspipes.share_authentication_server.logic.domain.User;
import pt.isel.ngspipes.share_authentication_server.logic.service.ICurrentUserSupplier;
import pt.isel.ngspipes.share_authentication_server.logic.service.PermissionService.Access;
import pt.isel.ngspipes.share_core.logic.service.exceptions.ServiceException;
import pt.isel.ngspipes.share_pipelines_server_repository.logic.domain.PipelinesRepositoryMetadata;
import pt.isel.ngspipes.share_pipelines_server_repository.logic.service.PermissionService;
import pt.isel.ngspipes.share_pipelines_server_repository.logic.service.repositoryMetadata.IPipelinesRepositoryMetadataService;
import pt.isel.ngspipes.share_pipelines_server_repository.logic.service.repositoryService.IPipelinesRepositoryService;
import pt.isel.ngspipes.share_pipelines_server_repository.serviceInterface.controller.facade.IPipelinesRepositoryController;

import java.util.Collection;

@RestController
public class PipelinesRepositoryController implements IPipelinesRepositoryController {

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private ICurrentUserSupplier currentUserSupplier;
    @Autowired
    private IPipelinesRepositoryMetadataService repositoryMetadataService;
    @Autowired
    private IPipelinesRepositoryService repositoryService;



    public ResponseEntity<Collection<PipelinesRepositoryMetadata>> getAllRepositories() throws Exception {
        if(!isValidAccess(Access.Operation.GET, null))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        Collection<PipelinesRepositoryMetadata> repositories = repositoryMetadataService.getAll();

        hidePasswords(repositories);

        return new ResponseEntity<>(repositories, HttpStatus.OK);
    }

    public ResponseEntity<PipelinesRepositoryMetadata> getRepository(@PathVariable int repositoryId) throws Exception {
        if(!isValidAccess(Access.Operation.GET, repositoryId))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        PipelinesRepositoryMetadata repository = repositoryMetadataService.getById(repositoryId);

        if(repository != null)
            hidePasswords(repository);

        return new ResponseEntity<>(repository, HttpStatus.OK);
    }

    public ResponseEntity<Integer> insertRepository(@RequestBody PipelinesRepositoryMetadata repository) throws Exception {
        if(!isValidAccess(Access.Operation.INSERT, repository.getId()))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        repositoryService.createRepository(repository);

        return new ResponseEntity<>(repository.getId(), HttpStatus.CREATED);
    }

    public ResponseEntity<Void> updateRepository(@RequestBody PipelinesRepositoryMetadata repository, @PathVariable int repositoryId) throws Exception {
        if(repository.getId() != repositoryId)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if(!isValidAccess(Access.Operation.UPDATE, repositoryId))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        repositoryMetadataService.update(repository);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteRepository(@PathVariable int repositoryId) throws Exception {
        if(!isValidAccess(Access.Operation.DELETE, repositoryId))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        repositoryService.deleteRepository(repositoryId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Collection<PipelinesRepositoryMetadata>> getPipelinesRepositoriesOfUser(@PathVariable String userName) throws Exception {
        if(!isValidAccess(Access.Operation.GET, null))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        Collection<PipelinesRepositoryMetadata> repositories = repositoryMetadataService.getPipelinesRepositoriesOfUser(userName);

        hidePasswords(repositories);

        return new ResponseEntity<>(repositories, HttpStatus.OK);
    }


    private boolean isValidAccess(Access.Operation operation, Integer repositoryId) throws ServiceException {
        User currentUser = currentUserSupplier.get();

        Access access = new Access();
        access.userName = currentUser == null ? null : currentUser.getUserName();
        access.operation = operation;
        access.entity = PipelinesRepositoryMetadata.class;
        access.entityId = repositoryId == null ? null : Integer.toString(repositoryId);

        return permissionService.isValid(access);
    }

    private void hidePasswords(Collection<PipelinesRepositoryMetadata> repositories) {
        for(PipelinesRepositoryMetadata repository : repositories)
            hidePasswords(repository);
    }

    private void hidePasswords(PipelinesRepositoryMetadata repository) {
        repository.getOwner().setPassword("");
    }

}

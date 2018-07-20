package pt.isel.ngspipes.share_pipelines_server_repository.serviceInterface.controller.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pt.isel.ngspipes.share_core.logic.domain.Group;
import pt.isel.ngspipes.share_core.logic.domain.PipelinesRepository;
import pt.isel.ngspipes.share_core.logic.domain.ToolsRepository;
import pt.isel.ngspipes.share_core.logic.domain.User;
import pt.isel.ngspipes.share_core.logic.service.ICurrentUserSupplier;
import pt.isel.ngspipes.share_core.logic.service.PermissionService;
import pt.isel.ngspipes.share_core.logic.service.PermissionService.Access;
import pt.isel.ngspipes.share_core.logic.service.exceptions.ServiceException;
import pt.isel.ngspipes.share_core.logic.service.pipelinesRepository.PipelinesRepositoryService;
import pt.isel.ngspipes.share_pipelines_server_repository.serviceInterface.controller.facade.IRepositoryController;

import java.util.Collection;

@RestController
public class RepositoryController implements IRepositoryController {

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private ICurrentUserSupplier currentUserSupplier;
    @Autowired
    private PipelinesRepositoryService repositoryService;



    public ResponseEntity<Collection<PipelinesRepository>> getAllRepositories() throws Exception {
        if(!isValidAccess(Access.Operation.GET, null))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        Collection<PipelinesRepository> repositories = repositoryService.getAll();

        hidePasswords(repositories);

        return new ResponseEntity<>(repositories, HttpStatus.OK);
    }

    public ResponseEntity<PipelinesRepository> getRepository(@PathVariable int repositoryId) throws Exception {
        if(!isValidAccess(Access.Operation.GET, repositoryId))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        PipelinesRepository repository = repositoryService.getById(repositoryId);

        if(repository != null)
            hidePasswords(repository);

        return new ResponseEntity<>(repository, HttpStatus.OK);
    }

    public ResponseEntity<Integer> insertRepository(@RequestBody PipelinesRepository repository) throws Exception {
        if(!isValidAccess(Access.Operation.INSERT, repository.getId()))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        repositoryService.insert(repository);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<Void> updateRepository(@RequestBody PipelinesRepository repository, @PathVariable int repositoryId) throws Exception {
        if(repository.getId() != repositoryId)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if(!isValidAccess(Access.Operation.UPDATE, repositoryId))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        repositoryService.update(repository);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteRepository(@PathVariable int repositoryId) throws Exception {
        if(!isValidAccess(Access.Operation.DELETE, repositoryId))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        repositoryService.delete(repositoryId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Collection<PipelinesRepository>> getPipelinesRepositoriesOfUser(String userName) throws Exception {
        if(!isValidAccess(Access.Operation.GET, null))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        Collection<PipelinesRepository> repositories = repositoryService.getPipelinesRepositoriesOfUser(userName);

        hidePasswords(repositories);

        return new ResponseEntity<>(repositories, HttpStatus.OK);
    }


    private boolean isValidAccess(Access.Operation operation, Integer repositoryId) throws ServiceException {
        User currentUser = currentUserSupplier.get();

        Access access = new Access();
        access.userName = currentUser == null ? null : currentUser.getUserName();
        access.operation = operation;
        access.entity = ToolsRepository.class;
        access.entityId = Integer.toString(repositoryId);

        return permissionService.isValid(access);
    }

    private void hidePasswords(Collection<PipelinesRepository> repositories) {
        for(PipelinesRepository repository : repositories)
            hidePasswords(repository);
    }

    private void hidePasswords(PipelinesRepository repository) {
        repository.getOwner().setPassword("");

        for(User user : repository.getUsersAccess())
            user.setPassword("");

        for(Group group : repository.getGroupAccess())
            for(User member : group.getMembers())
                member.setPassword("");
    }

}

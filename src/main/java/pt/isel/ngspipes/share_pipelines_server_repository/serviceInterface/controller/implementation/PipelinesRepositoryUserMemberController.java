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
import pt.isel.ngspipes.share_pipelines_server_repository.logic.domain.PipelinesRepositoryUserMember;
import pt.isel.ngspipes.share_pipelines_server_repository.logic.service.PermissionService;
import pt.isel.ngspipes.share_pipelines_server_repository.logic.service.repositoryUserMember.IPipelinesRepositoryUserMemberService;
import pt.isel.ngspipes.share_pipelines_server_repository.serviceInterface.controller.facade.IPipelinesRepositoryUserMemberController;

import java.util.Collection;

@RestController
public class PipelinesRepositoryUserMemberController implements IPipelinesRepositoryUserMemberController {

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private ICurrentUserSupplier currentUserSupplier;
    @Autowired
    private IPipelinesRepositoryUserMemberService userMemberService;


    @Override
    public ResponseEntity<Collection<PipelinesRepositoryUserMember>> getAllMembers() throws Exception {
        if(!isValidAccess(Access.Operation.GET, null))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        Collection<PipelinesRepositoryUserMember> members = userMemberService.getAll();

        hidePasswords(members);

        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PipelinesRepositoryUserMember> getMember(@PathVariable int memberId) throws Exception {
        if(!isValidAccess(Access.Operation.GET, memberId))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        PipelinesRepositoryUserMember member = userMemberService.getById(memberId);

        if(member != null)
            hidePasswords(member);

        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Integer> insertMember(@RequestBody PipelinesRepositoryUserMember member) throws Exception {
        if(!isValidInsertAccess(member.getRepository().getId()))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        userMemberService.insert(member);

        return new ResponseEntity<>(member.getId(), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> updateMember(@RequestBody PipelinesRepositoryUserMember member, @PathVariable int memberId) throws Exception {
        if(member.getId() != memberId)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if(!isValidAccess(Access.Operation.UPDATE, memberId))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        userMemberService.update(member);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteMember(@PathVariable int memberId) throws Exception {
        if(!isValidAccess(Access.Operation.DELETE, memberId))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        userMemberService.delete(memberId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Collection<PipelinesRepositoryUserMember>> getMembersWithUser(@PathVariable String userName) throws ServiceException {
        if(!isValidAccess(Access.Operation.GET, null))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        Collection<PipelinesRepositoryUserMember> members = userMemberService.getMembersWithUser(userName);

        hidePasswords(members);

        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Collection<PipelinesRepositoryUserMember>> getMembersOfRepository(@PathVariable int repositoryId) throws ServiceException {
        if(!isValidAccess(Access.Operation.GET, null))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        Collection<PipelinesRepositoryUserMember> members = userMemberService.getMembersOfRepository(repositoryId);

        hidePasswords(members);

        return new ResponseEntity<>(members, HttpStatus.OK);
    }


    private boolean isValidAccess(Access.Operation operation, Integer memberId) throws ServiceException {
        User currentUser = currentUserSupplier.get();

        Access access = new Access();
        access.userName = currentUser == null ? null : currentUser.getUserName();
        access.operation = operation;
        access.entity = PipelinesRepositoryUserMember.class;
        access.entityId = memberId == null ? null : Integer.toString(memberId);

        return permissionService.isValid(access);
    }

    private boolean isValidInsertAccess(Integer repositoryId) throws ServiceException {
        User currentUser = currentUserSupplier.get();

        Access access = new Access();
        access.userName = currentUser == null ? null : currentUser.getUserName();
        access.operation = Access.Operation.UPDATE;
        access.entity = PipelinesRepositoryMetadata.class;
        access.entityId = repositoryId == null ? null : Integer.toString(repositoryId);

        return permissionService.isValid(access);
    }

    private void hidePasswords(Collection<PipelinesRepositoryUserMember> members) {
        for(PipelinesRepositoryUserMember member : members)
            hidePasswords(member);
    }

    private void hidePasswords(PipelinesRepositoryUserMember member) {
        member.getUser().setPassword("");
    }

}

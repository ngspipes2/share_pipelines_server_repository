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
import pt.isel.ngspipes.share_pipelines_server_repository.logic.domain.PipelinesRepositoryGroupMember;
import pt.isel.ngspipes.share_pipelines_server_repository.logic.domain.PipelinesRepositoryMetadata;
import pt.isel.ngspipes.share_pipelines_server_repository.logic.service.PermissionService;
import pt.isel.ngspipes.share_pipelines_server_repository.logic.service.repositoryGroupMember.IPipelinesRepositoryGroupMemberService;
import pt.isel.ngspipes.share_pipelines_server_repository.serviceInterface.controller.facade.IPipelinesRepositoryGroupMemberController;

import java.util.Collection;

@RestController
public class PipelinesRepositoryGroupMemberController implements IPipelinesRepositoryGroupMemberController {

    @Autowired
    private PermissionService permissionService;
    @Autowired
    private ICurrentUserSupplier currentUserSupplier;
    @Autowired
    private IPipelinesRepositoryGroupMemberService groupMemberService;


    @Override
    public ResponseEntity<Collection<PipelinesRepositoryGroupMember>> getAllMembers() throws Exception {
        if(!isValidAccess(Access.Operation.GET, null))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        Collection<PipelinesRepositoryGroupMember> members = groupMemberService.getAll();

        hidePasswords(members);

        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PipelinesRepositoryGroupMember> getMember(@PathVariable int memberId) throws Exception {
        if(!isValidAccess(Access.Operation.GET, memberId))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        PipelinesRepositoryGroupMember member = groupMemberService.getById(memberId);

        if(member != null)
            hidePasswords(member);

        return new ResponseEntity<>(member, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Integer> insertMember(@RequestBody PipelinesRepositoryGroupMember member) throws Exception {
        if(!isValidInsertAccess(member.getRepository().getId()))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        groupMemberService.insert(member);

        return new ResponseEntity<>(member.getId(), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> updateMember(@RequestBody PipelinesRepositoryGroupMember member, @PathVariable int memberId) throws Exception {
        if(member.getId() != memberId)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        if(!isValidAccess(Access.Operation.UPDATE, memberId))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        groupMemberService.update(member);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteMember(@PathVariable int memberId) throws Exception {
        if(!isValidAccess(Access.Operation.DELETE, memberId))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        groupMemberService.delete(memberId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Collection<PipelinesRepositoryGroupMember>> getMembersWithGroup(@PathVariable String groupName) throws ServiceException {
        if(!isValidAccess(Access.Operation.GET, null))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        Collection<PipelinesRepositoryGroupMember> members = groupMemberService.getMembersWithGroup(groupName);

        hidePasswords(members);

        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Collection<PipelinesRepositoryGroupMember>> getMembersOfRepository(@PathVariable int repositoryId) throws ServiceException {
        if(!isValidAccess(Access.Operation.GET, null))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        Collection<PipelinesRepositoryGroupMember> members = groupMemberService.getMembersOfRepository(repositoryId);

        hidePasswords(members);

        return new ResponseEntity<>(members, HttpStatus.OK);
    }


    private boolean isValidAccess(Access.Operation operation, Integer memberId) throws ServiceException {
        User currentUser = currentUserSupplier.get();

        Access access = new Access();
        access.userName = currentUser == null ? null : currentUser.getUserName();
        access.operation = operation;
        access.entity = PipelinesRepositoryGroupMember.class;
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

    private void hidePasswords(Collection<PipelinesRepositoryGroupMember> members) {
        for(PipelinesRepositoryGroupMember member : members)
            hidePasswords(member);
    }

    private void hidePasswords(PipelinesRepositoryGroupMember member) {
        member.getGroup().getOwner().setPassword("");
    }

}

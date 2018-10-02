package pt.isel.ngspipes.share_pipelines_server_repository.logic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pt.isel.ngspipes.share_authentication_server.logic.domain.User;
import pt.isel.ngspipes.share_authentication_server.logic.service.PermissionService.Access;
import pt.isel.ngspipes.share_authentication_server.logic.service.group.IGroupService;
import pt.isel.ngspipes.share_core.logic.service.IService;
import pt.isel.ngspipes.share_core.logic.service.exceptions.ServiceException;
import pt.isel.ngspipes.share_pipelines_server_repository.logic.domain.PipelinesRepositoryGroupMember;
import pt.isel.ngspipes.share_pipelines_server_repository.logic.domain.PipelinesRepositoryMetadata;
import pt.isel.ngspipes.share_pipelines_server_repository.logic.domain.PipelinesRepositoryUserMember;
import pt.isel.ngspipes.share_pipelines_server_repository.logic.service.repositoryGroupMember.IPipelinesRepositoryGroupMemberService;
import pt.isel.ngspipes.share_pipelines_server_repository.logic.service.repositoryMetadata.IPipelinesRepositoryMetadataService;
import pt.isel.ngspipes.share_pipelines_server_repository.logic.service.repositoryUserMember.IPipelinesRepositoryUserMemberService;

@Service
public class PermissionService {

    @Autowired
    private IService<User, String> userService;

    @Autowired
    private IGroupService groupService;

    @Autowired
    private IPipelinesRepositoryMetadataService pipelinesRepositoryService;

    @Autowired
    private IPipelinesRepositoryUserMemberService userMemberService;

    @Autowired
    private IPipelinesRepositoryGroupMemberService groupMemberService;



    @Transactional
    public boolean isValid(Access access) throws ServiceException {
        if(access.operation.equals(Access.Operation.GET))
            return true;

        User user = access.userName == null ? null : userService.getById(access.userName);
        if(user != null && user.getRole().equals(User.Role.ADMIN))
            return true;

        switch (access.entity.getSimpleName()) {
            case "PipelinesRepositoryMetadata": return isValidPipelinesRepositoryAccess(access);
            case "PipelinesRepositoryUserMember": return isValidUserMemberAccess(access);
            case "PipelinesRepositoryGroupMember": return isValidGroupMemberAccess(access);
            default: throw new ServiceException("Unknown entity " + access.entity.getSimpleName() + "!");
        }
    }

    private boolean isValidPipelinesRepositoryAccess(Access access) throws ServiceException {
        PipelinesRepositoryMetadata repository = access.entityId == null ? null : pipelinesRepositoryService.getById(Integer.parseInt(access.entityId));

        if(repository == null)//accessing non existent PipelinesRepositoryMetadata
            return true;

        return repository.getOwner().getUserName().equals(access.userName);
    }

    private boolean isValidUserMemberAccess(Access access) throws ServiceException {
        PipelinesRepositoryUserMember member = access.entityId == null ? null : userMemberService.getById(Integer.parseInt(access.entityId));

        if(member == null)//accessing non existent Member
            return true;

        return member.getRepository().getOwner().getUserName().equals(access.userName);
    }

    private boolean isValidGroupMemberAccess(Access access) throws ServiceException {
        PipelinesRepositoryGroupMember member = access.entityId == null ? null : groupMemberService.getById(Integer.parseInt(access.entityId));

        if(member == null)//accessing non existent Member
            return true;

        return member.getRepository().getOwner().getUserName().equals(access.userName);
    }

}

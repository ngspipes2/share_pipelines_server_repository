package pt.isel.ngspipes.share_pipelines_server_repository.logic.service.repositoryGroupMember;

import pt.isel.ngspipes.share_core.logic.service.IService;
import pt.isel.ngspipes.share_core.logic.service.exceptions.ServiceException;
import pt.isel.ngspipes.share_pipelines_server_repository.logic.domain.PipelinesRepositoryGroupMember;

import java.util.Collection;

public interface IPipelinesRepositoryGroupMemberService extends IService<PipelinesRepositoryGroupMember, Integer> {

    Collection<PipelinesRepositoryGroupMember> getMembersWithGroup(String groupName) throws ServiceException;

    Collection<PipelinesRepositoryGroupMember> getMembersOfRepository(int repositoryId) throws ServiceException;

}

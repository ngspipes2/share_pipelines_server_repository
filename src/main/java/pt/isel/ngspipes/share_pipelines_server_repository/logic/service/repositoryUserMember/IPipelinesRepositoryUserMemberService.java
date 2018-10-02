package pt.isel.ngspipes.share_pipelines_server_repository.logic.service.repositoryUserMember;

import pt.isel.ngspipes.share_core.logic.service.IService;
import pt.isel.ngspipes.share_core.logic.service.exceptions.ServiceException;
import pt.isel.ngspipes.share_pipelines_server_repository.logic.domain.PipelinesRepositoryUserMember;

import java.util.Collection;

public interface IPipelinesRepositoryUserMemberService extends IService<PipelinesRepositoryUserMember, Integer> {

    Collection<PipelinesRepositoryUserMember> getMembersWithUser(String userName) throws ServiceException;

    Collection<PipelinesRepositoryUserMember> getMembersOfRepository(int repositoryId) throws ServiceException;

}

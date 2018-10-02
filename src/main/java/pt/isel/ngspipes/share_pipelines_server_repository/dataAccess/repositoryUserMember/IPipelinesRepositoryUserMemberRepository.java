package pt.isel.ngspipes.share_pipelines_server_repository.dataAccess.repositoryUserMember;

import pt.isel.ngspipes.share_core.dataAccess.IRepository;
import pt.isel.ngspipes.share_core.dataAccess.RepositoryException;
import pt.isel.ngspipes.share_pipelines_server_repository.logic.domain.PipelinesRepositoryUserMember;

import java.util.Collection;

public interface IPipelinesRepositoryUserMemberRepository extends IRepository<PipelinesRepositoryUserMember, Integer> {

    Collection<PipelinesRepositoryUserMember> getMembersOfRepository(int repositoryId) throws RepositoryException;

    Collection<PipelinesRepositoryUserMember> getMembersWithUser(String userName) throws RepositoryException;

}

package pt.isel.ngspipes.share_pipelines_server_repository.dataAccess.repositoryGroupMember;

import pt.isel.ngspipes.share_core.dataAccess.IRepository;
import pt.isel.ngspipes.share_core.dataAccess.RepositoryException;
import pt.isel.ngspipes.share_pipelines_server_repository.logic.domain.PipelinesRepositoryGroupMember;

import java.util.Collection;

public interface IPipelinesRepositoryGroupMemberRepository extends IRepository<PipelinesRepositoryGroupMember, Integer> {

    Collection<PipelinesRepositoryGroupMember> getMembersOfRepository(int repositoryId) throws RepositoryException;

    Collection<PipelinesRepositoryGroupMember> getMembersWithGroup(String groupName) throws RepositoryException;

}

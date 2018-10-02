package pt.isel.ngspipes.share_pipelines_server_repository.dataAccess.repositoryGroupMember;

import pt.isel.ngspipes.share_core.dataAccess.PostgresRepository;
import pt.isel.ngspipes.share_core.dataAccess.RepositoryException;
import pt.isel.ngspipes.share_pipelines_server_repository.logic.domain.PipelinesRepositoryGroupMember;

import java.util.Collection;
import java.util.stream.Collectors;

@org.springframework.stereotype.Repository
public class PipelinesRepositoryGroupMemberRepository extends PostgresRepository<PipelinesRepositoryGroupMember, Integer> implements IPipelinesRepositoryGroupMemberRepository {

    public PipelinesRepositoryGroupMemberRepository() {
        super(PipelinesRepositoryGroupMember.class);
    }



    @Override
    protected void setId(PipelinesRepositoryGroupMember member, Integer id) {
        member.setId(id);
    }

    @Override
    protected Integer getId(PipelinesRepositoryGroupMember member) {
        return member.getId();
    }

    @Override
    protected void merge(PipelinesRepositoryGroupMember member, PipelinesRepositoryGroupMember memberToUpdate) {
        memberToUpdate.setId(member.getId());
        memberToUpdate.setCreationDate(member.getCreationDate());
        memberToUpdate.setGroup(member.getGroup());
        memberToUpdate.setRepository(member.getRepository());
        memberToUpdate.setWriteAccess(member.getWriteAccess());
    }

    @Override
    public Collection<PipelinesRepositoryGroupMember> getMembersOfRepository(int repositoryId) throws RepositoryException {
        return getAll().stream().filter((member) -> member.getRepository().getId() == repositoryId).collect(Collectors.toList());
    }

    @Override
    public Collection<PipelinesRepositoryGroupMember> getMembersWithGroup(String groupName) throws RepositoryException {
        return getAll().stream().filter((member) -> member.getGroup().getGroupName().equals(groupName)).collect(Collectors.toList());
    }
}

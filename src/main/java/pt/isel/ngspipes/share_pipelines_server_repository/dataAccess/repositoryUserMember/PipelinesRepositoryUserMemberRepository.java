package pt.isel.ngspipes.share_pipelines_server_repository.dataAccess.repositoryUserMember;

import pt.isel.ngspipes.share_core.dataAccess.PostgresRepository;
import pt.isel.ngspipes.share_core.dataAccess.RepositoryException;
import pt.isel.ngspipes.share_pipelines_server_repository.logic.domain.PipelinesRepositoryUserMember;

import java.util.Collection;
import java.util.stream.Collectors;

@org.springframework.stereotype.Repository
public class PipelinesRepositoryUserMemberRepository extends PostgresRepository<PipelinesRepositoryUserMember, Integer> implements IPipelinesRepositoryUserMemberRepository {

    public PipelinesRepositoryUserMemberRepository() {
        super(PipelinesRepositoryUserMember.class);
    }



    @Override
    protected void setId(PipelinesRepositoryUserMember member, Integer id) {
        member.setId(id);
    }

    @Override
    protected Integer getId(PipelinesRepositoryUserMember member) {
        return member.getId();
    }

    @Override
    protected void merge(PipelinesRepositoryUserMember member, PipelinesRepositoryUserMember memberToUpdate) {
        memberToUpdate.setId(member.getId());
        memberToUpdate.setCreationDate(member.getCreationDate());
        memberToUpdate.setUser(member.getUser());
        memberToUpdate.setRepository(member.getRepository());
        memberToUpdate.setWriteAccess(member.getWriteAccess());
    }

    @Override
    public Collection<PipelinesRepositoryUserMember> getMembersOfRepository(int repositoryId) throws RepositoryException {
        return getAll().stream().filter((member) -> member.getRepository().getId() == repositoryId).collect(Collectors.toList());
    }

    @Override
    public Collection<PipelinesRepositoryUserMember> getMembersWithUser(String userName) throws RepositoryException {
        return getAll().stream().filter((member) -> member.getUser().getUserName().equals(userName)).collect(Collectors.toList());
    }
}

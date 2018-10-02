package pt.isel.ngspipes.share_pipelines_server_repository.logic.service.repositoryGroupMember;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import pt.isel.ngspipes.share_core.dataAccess.RepositoryException;
import pt.isel.ngspipes.share_core.logic.service.Service;
import pt.isel.ngspipes.share_core.logic.service.ServiceUtils;
import pt.isel.ngspipes.share_core.logic.service.exceptions.ServiceException;
import pt.isel.ngspipes.share_pipelines_server_repository.dataAccess.repositoryGroupMember.IPipelinesRepositoryGroupMemberRepository;
import pt.isel.ngspipes.share_pipelines_server_repository.logic.domain.PipelinesRepositoryGroupMember;

import java.util.Collection;
import java.util.Date;

@org.springframework.stereotype.Service
public class PipelinesRepositoryGroupMemberService extends Service<PipelinesRepositoryGroupMember, Integer> implements IPipelinesRepositoryGroupMemberService {

    @Autowired
    private IPipelinesRepositoryGroupMemberRepository groupMemberRepository;



    protected PipelinesRepositoryGroupMemberService() {
        super("RepositoryGroupMembers", "PipelinesRepositoryGroupMember");
    }



    @Override
    @Transactional
    public void insert(PipelinesRepositoryGroupMember member) throws ServiceException {
        member.setCreationDate(new Date());
        super.insert(member);
    }

    @Override
    protected void validateInsert(PipelinesRepositoryGroupMember member) throws ServiceException { }

    @Override
    protected void validateDelete(Integer id) throws ServiceException { }

    @Override
    protected void validateUpdate(PipelinesRepositoryGroupMember member) throws ServiceException {
        PipelinesRepositoryGroupMember savedMember = getById(member.getId());

        if(savedMember != null) {
            if(!ServiceUtils.sameDate(savedMember.getCreationDate(), member.getCreationDate()))
                throw new ServiceException("Members's creationDate Cannot be changed!");

            if(!savedMember.getGroup().getGroupName().equals(member.getGroup().getGroupName()))
                throw new ServiceException("Members's group Cannot be changed!");

            if(savedMember.getRepository().getId() != member.getRepository().getId())
                throw new ServiceException("Members's repository Cannot be changed!");
        }
    }

    @Override
    protected Integer getId(PipelinesRepositoryGroupMember member) throws ServiceException {
        return member.getId();
    }

    @Override
    public Collection<PipelinesRepositoryGroupMember> getMembersWithGroup(String groupName) throws ServiceException {
        try {
            return groupMemberRepository.getMembersWithGroup(groupName);
        } catch (RepositoryException e) {
            throw new ServiceException("Error getting Members with Group!");
        }
    }

    @Override
    public Collection<PipelinesRepositoryGroupMember> getMembersOfRepository(int repositoryId) throws ServiceException {
        try {
            return groupMemberRepository.getMembersOfRepository(repositoryId);
        } catch (RepositoryException e) {
            throw new ServiceException("Error getting Members of Repository!");
        }
    }

}

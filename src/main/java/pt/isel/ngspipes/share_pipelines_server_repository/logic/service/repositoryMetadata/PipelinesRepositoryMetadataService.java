package pt.isel.ngspipes.share_pipelines_server_repository.logic.service.repositoryMetadata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import pt.isel.ngspipes.share_authentication_server.logic.service.ICurrentUserSupplier;
import pt.isel.ngspipes.share_core.dataAccess.RepositoryException;
import pt.isel.ngspipes.share_core.logic.service.Service;
import pt.isel.ngspipes.share_core.logic.service.ServiceUtils;
import pt.isel.ngspipes.share_core.logic.service.exceptions.ServiceException;
import pt.isel.ngspipes.share_pipelines_server_repository.dataAccess.pipelinesRepository.IPipelinesRepositoryMetadataRepository;
import pt.isel.ngspipes.share_pipelines_server_repository.logic.domain.PipelinesRepositoryMetadata;

import java.util.Collection;
import java.util.Date;

@org.springframework.stereotype.Service
public class PipelinesRepositoryMetadataService extends Service<PipelinesRepositoryMetadata, Integer> implements IPipelinesRepositoryMetadataService {

    @Autowired
    private IPipelinesRepositoryMetadataRepository pipelinesRepositoryRepository;
    @Autowired
    private ICurrentUserSupplier currentUserSupplier;



    protected PipelinesRepositoryMetadataService() {
        super("RepositoriesMetadata", "PipelinesRepositoryMetadata");
    }



    @Override
    @Transactional
    public void insert(PipelinesRepositoryMetadata repository) throws ServiceException {
        repository.setCreationDate(new Date());
        repository.setOwner(currentUserSupplier.get());

        super.insert(repository);
    }

    @Override
    protected void validateInsert(PipelinesRepositoryMetadata repository) throws ServiceException { }

    @Override
    protected void validateDelete(Integer id) throws ServiceException { }

    @Override
    protected void validateUpdate(PipelinesRepositoryMetadata repository) throws ServiceException {
        PipelinesRepositoryMetadata savedRepository = getById(repository.getId());

        if(repository != null) {
            if(!ServiceUtils.sameDate(savedRepository.getCreationDate(), repository.getCreationDate()))
                throw new ServiceException("PipelinesRepositoryMetadata's creationDate Cannot be changed!");

            if(!savedRepository.getOwner().getUserName().equals(repository.getOwner().getUserName()))
                throw new ServiceException("PipelinesRepositoryMetadata's owner Cannot be changed!");
        }
    }

    @Override
    protected Integer getId(PipelinesRepositoryMetadata repository) throws ServiceException {
        return repository.getId();
    }

    @Override
    public Collection<PipelinesRepositoryMetadata> getPipelinesRepositoriesOfUser(String userName) throws ServiceException {
        try {
            return pipelinesRepositoryRepository.getRepositoriesOfUser(userName);
        } catch (RepositoryException e) {
            throw new ServiceException("Error getting Repositories of User!");
        }
    }

}

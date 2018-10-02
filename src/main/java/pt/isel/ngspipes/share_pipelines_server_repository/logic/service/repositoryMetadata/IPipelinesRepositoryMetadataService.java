package pt.isel.ngspipes.share_pipelines_server_repository.logic.service.repositoryMetadata;

import pt.isel.ngspipes.share_core.logic.service.IService;
import pt.isel.ngspipes.share_core.logic.service.exceptions.ServiceException;
import pt.isel.ngspipes.share_pipelines_server_repository.logic.domain.PipelinesRepositoryMetadata;

import java.util.Collection;

public interface IPipelinesRepositoryMetadataService extends IService<PipelinesRepositoryMetadata, Integer> {

    Collection<PipelinesRepositoryMetadata> getPipelinesRepositoriesOfUser(String userName) throws ServiceException;

}

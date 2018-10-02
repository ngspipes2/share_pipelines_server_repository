package pt.isel.ngspipes.share_pipelines_server_repository.logic.service.repositoryService;

import pt.isel.ngspipes.pipeline_repository.IPipelinesRepository;
import pt.isel.ngspipes.share_core.logic.service.exceptions.ServiceException;
import pt.isel.ngspipes.share_pipelines_server_repository.logic.domain.PipelinesRepositoryMetadata;


public interface IPipelinesRepositoryService {

    void createRepository(PipelinesRepositoryMetadata repo) throws ServiceException;

    void deleteRepository(int id) throws ServiceException;

    IPipelinesRepository getRepository(int id) throws ServiceException;

}

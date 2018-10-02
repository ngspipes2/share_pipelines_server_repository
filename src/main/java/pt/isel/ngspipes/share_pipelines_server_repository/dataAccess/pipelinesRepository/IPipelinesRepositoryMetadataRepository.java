package pt.isel.ngspipes.share_pipelines_server_repository.dataAccess.pipelinesRepository;

import pt.isel.ngspipes.share_core.dataAccess.IRepository;
import pt.isel.ngspipes.share_core.dataAccess.RepositoryException;
import pt.isel.ngspipes.share_pipelines_server_repository.logic.domain.PipelinesRepositoryMetadata;

import java.util.Collection;

public interface IPipelinesRepositoryMetadataRepository extends IRepository<PipelinesRepositoryMetadata, Integer> {

    Collection<PipelinesRepositoryMetadata> getRepositoriesOfUser(String userName) throws RepositoryException;

}

package pt.isel.ngspipes.share_pipelines_server_repository.dataAccess.pipelinesRepository;

import pt.isel.ngspipes.share_core.dataAccess.PostgresRepository;
import pt.isel.ngspipes.share_core.dataAccess.RepositoryException;
import pt.isel.ngspipes.share_pipelines_server_repository.logic.domain.PipelinesRepositoryMetadata;

import java.util.Collection;
import java.util.stream.Collectors;

@org.springframework.stereotype.Repository
public class PipelinesRepositoryMetadataRepository extends PostgresRepository<PipelinesRepositoryMetadata, Integer> implements IPipelinesRepositoryMetadataRepository {

    public PipelinesRepositoryMetadataRepository() {
        super(PipelinesRepositoryMetadata.class);
    }



    @Override
    protected void setId(PipelinesRepositoryMetadata repository, Integer id) {
        repository.setId(id);
    }

    @Override
    protected Integer getId(PipelinesRepositoryMetadata repository) {
        return repository.getId();
    }

    @Override
    protected void merge(PipelinesRepositoryMetadata repository, PipelinesRepositoryMetadata repositoryToUpdate) {
        repositoryToUpdate.setId(repository.getId());
        repositoryToUpdate.setName(repository.getName());
        repositoryToUpdate.setDescription(repository.getDescription());
        repositoryToUpdate.setCreationDate(repository.getCreationDate());
        repositoryToUpdate.setPublic(repository.getIsPublic());
        repositoryToUpdate.setOwner(repository.getOwner());
    }

    @Override
    public Collection<PipelinesRepositoryMetadata> getRepositoriesOfUser(String userName) throws RepositoryException {
        return getAll().stream().filter((repository) -> repository.getOwner().getUserName().equals(userName)).collect(Collectors.toList());
    }

}

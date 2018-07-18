package pt.isel.ngspipes.share_pipelines_server_repository.serviceInterface.config;

public class Routes {

    public static final String PIPELINES_REPOSITORIES = "/pipelinesrepositories";
    public static final String GET_ALL_PIPELINES_REPOSITORIES = PIPELINES_REPOSITORIES;
    public static final String GET_PIPELINES_REPOSITORY = PIPELINES_REPOSITORIES + "/{repositoryId}";
    public static final String INSERT_PIPELINES_REPOSITORY = PIPELINES_REPOSITORIES;
    public static final String UPDATE_PIPELINES_REPOSITORY = PIPELINES_REPOSITORIES + "/{repositoryId}";
    public static final String DELETE_PIPELINES_REPOSITORY = PIPELINES_REPOSITORIES + "/{repositoryId}";

}

package pt.isel.ngspipes.share_pipelines_server_repository.serviceInterface.config;

public class Routes {

    public static final String PIPELINES_REPOSITORY_SERVER_URI = "/server/{repositoryId}";

    public static final String GET_LOGO_URI = PIPELINES_REPOSITORY_SERVER_URI + "/logo";
    public static final String SET_LOGO_URI = PIPELINES_REPOSITORY_SERVER_URI + "/logo";
    public static final String GET_ALL_PIPELINES_URI = PIPELINES_REPOSITORY_SERVER_URI + "/pipelines";
    public static final String GET_PIPELINE_URI = PIPELINES_REPOSITORY_SERVER_URI + "/pipelines/{pipelineName}";
    public static final String INSERT_PIPELINE_URI = PIPELINES_REPOSITORY_SERVER_URI + "/pipelines";
    public static final String UPDATE_PIPELINE_URI = PIPELINES_REPOSITORY_SERVER_URI + "/pipelines/{pipelineName}";
    public static final String DELETE_PIPELINE_URI = PIPELINES_REPOSITORY_SERVER_URI + "/pipelines/{pipelineName}";

}

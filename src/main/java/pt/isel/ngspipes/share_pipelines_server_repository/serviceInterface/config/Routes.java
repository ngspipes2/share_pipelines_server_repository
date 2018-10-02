package pt.isel.ngspipes.share_pipelines_server_repository.serviceInterface.config;

public class Routes {

    public static final String PIPELINES_REPOSITORIES = "/pipelinesrepositories";
    public static final String GET_ALL_PIPELINES_REPOSITORIES = PIPELINES_REPOSITORIES;
    public static final String GET_PIPELINES_REPOSITORY = PIPELINES_REPOSITORIES + "/{repositoryId}";
    public static final String INSERT_PIPELINES_REPOSITORY = PIPELINES_REPOSITORIES;
    public static final String UPDATE_PIPELINES_REPOSITORY = PIPELINES_REPOSITORIES + "/{repositoryId}";
    public static final String DELETE_PIPELINES_REPOSITORY = PIPELINES_REPOSITORIES + "/{repositoryId}";
    public static final String GET_PIPELINES_REPOSITORIES_OF_USER = "/pipelinesrepositories/owner/{userName}";

    public static final String USER_MEMBER_URI = "/userMember";
    public static final String GET_ALL_USER_MEMBERS = USER_MEMBER_URI;
    public static final String GET_USER_MEMBER = USER_MEMBER_URI + "/{memberId}";
    public static final String INSERT_USER_MEMBER = USER_MEMBER_URI;
    public static final String UPDATE_USER_MEMBER = USER_MEMBER_URI + "/{memberId}";
    public static final String DELETE_USER_MEMBER = USER_MEMBER_URI + "/{memberId}";
    public static final String GET_USER_MEMBERS_WITH_USER = USER_MEMBER_URI + "/user/{userName}";
    public static final String GET_USER_MEMBERS_OF_REPOSITORY = USER_MEMBER_URI + "/repository/{repositoryId}";

    public static final String GROUP_MEMBER_URI = "/groupMember";
    public static final String GET_ALL_GROUP_MEMBERS = GROUP_MEMBER_URI;
    public static final String GET_GROUP_MEMBER = GROUP_MEMBER_URI + "/{memberId}";
    public static final String INSERT_GROUP_MEMBER = GROUP_MEMBER_URI;
    public static final String UPDATE_GROUP_MEMBER = GROUP_MEMBER_URI + "/{memberId}";
    public static final String DELETE_GROUP_MEMBER = GROUP_MEMBER_URI + "/{memberId}";
    public static final String GET_GROUP_MEMBERS_WITH_GROUP = GROUP_MEMBER_URI + "/group/{groupName}";
    public static final String GET_GROUP_MEMBERS_OF_REPOSITORY = GROUP_MEMBER_URI + "/repository/{repositoryId}";

    public static final String IMAGES_URI = "/images";
    public static final String PIPELINES_REPOSITORY_IMAGE = IMAGES_URI + "/pipelinesRepository";
    public static final String GET_PIPELINES_REPOSITORY_IMAGE = PIPELINES_REPOSITORY_IMAGE + "/{repositoryId}";
    public static final String UPDATE_PIPELINES_REPOSITORY_IMAGE = PIPELINES_REPOSITORY_IMAGE + "/{repositoryId}";
    public static final String DELETE_PIPELINES_REPOSITORY_IMAGE = PIPELINES_REPOSITORY_IMAGE + "/{repositoryId}";


    public static final String PIPELINES_REPOSITORY_SERVER_URI = "/server";
    public static final String GET_ALL_PIPELINES_REPOSITORY_SERVER_URI = PIPELINES_REPOSITORY_SERVER_URI + "/{repositoryId}/pipelines";
    public static final String GET_PIPELINES_REPOSITORY_SERVER_URI = PIPELINES_REPOSITORY_SERVER_URI + "/{repositoryId}/pipelines/{pipelineName}";
    public static final String INSERT_PIPELINES_REPOSITORY_SERVER_URI = PIPELINES_REPOSITORY_SERVER_URI + "/{repositoryId}/pipelines";
    public static final String UPDATE_PIPELINES_REPOSITORY_SERVER_URI = PIPELINES_REPOSITORY_SERVER_URI + "/{repositoryId}/pipelines/{pipelineName}";
    public static final String DELETE_PIPELINES_REPOSITORY_SERVER_URI = PIPELINES_REPOSITORY_SERVER_URI + "/{repositoryId}/pipelines/{pipelineName}";

}

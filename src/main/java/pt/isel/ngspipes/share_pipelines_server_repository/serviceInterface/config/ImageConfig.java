package pt.isel.ngspipes.share_pipelines_server_repository.serviceInterface.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pt.isel.ngspipes.share_core.dataAccess.image.AmazonData;

@Configuration
public class ImageConfig {


    @Value("${s3.endpointUrl}")
    private String endpointUrl;

    @Value("${s3.accessKey}")
    private String accessKey;

    @Value("${s3.secretKey}")
    private String secretKey;

    @Value("${s3.bucketName}")
    private String bucketName;

    @Value("${s3.region}")
    private String region;



    @Bean
    public AmazonData getAmazonData() {
        AmazonData data = new AmazonData();
        data.endpointUrl = endpointUrl;
        data.accessKey = accessKey;
        data.secretKey = secretKey;
        data.bucketName = bucketName;
        data.region = region;

        return data;
    }

}

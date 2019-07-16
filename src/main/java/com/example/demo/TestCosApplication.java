package com.example.demo;

import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.ibm.cloud.objectstorage.ClientConfiguration;
import com.ibm.cloud.objectstorage.ClientConfigurationFactory;
import com.ibm.cloud.objectstorage.auth.AWSCredentials;
import com.ibm.cloud.objectstorage.auth.AWSStaticCredentialsProvider;
import com.ibm.cloud.objectstorage.auth.BasicAWSCredentials;
import com.ibm.cloud.objectstorage.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.ibm.cloud.objectstorage.oauth.BasicIBMOAuthCredentials;
import com.ibm.cloud.objectstorage.services.s3.AmazonS3;
import com.ibm.cloud.objectstorage.services.s3.AmazonS3ClientBuilder;

@SpringBootApplication
public class TestCosApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestCosApplication.class, args);
	}

	@Value("${cos.endpoint}")
    private URL endpoint;

    @Value("${cos.location}")
    private String location;

    @Value("${cos.api-key}")
    private String apiKey;
    
    @Value("${cos.service-instance-id}")
    private String serviceInstanceId;

//    @Bean
//    public AmazonS3ClientBuilder builder() {
////        return AmazonS3ClientBuilder.standard()
////        .withEndpointConfiguration(
////            new EndpointConfiguration(endpoint.toString(), location))
////        .withCredentials(new AWSStaticCredentialsProvider(new BasicIBMOAuthCredentials(apiKey, serviceInstanceId)))
////        .withPathStyleAccessEnabled(true);
//    	
//    	//return createClient(apiKey,serviceInstanceId,endpoint.toString(),location);
//    }
    
    @Bean
    public AmazonS3 builderAmazonS3() {
    	return createClient(apiKey,serviceInstanceId,endpoint.toString(),location);
    }
    
    public  AmazonS3 createClient(String api_key, String service_instance_id, String endpoint_url, String location)
    {
        AWSCredentials credentials;
        credentials = new BasicIBMOAuthCredentials(api_key, service_instance_id);

        ClientConfiguration clientConfig = new ClientConfiguration().withRequestTimeout(5000);
        clientConfig.setUseTcpKeepAlive(true);

        AmazonS3 cosClient = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withEndpointConfiguration(new EndpointConfiguration(endpoint_url, location)).withPathStyleAccessEnabled(true)
                .withClientConfiguration(clientConfig).build();
        return cosClient;
    }
    
    
    private AWSCredentials createAWSCredentials() {
       
          return new BasicIBMOAuthCredentials(apiKey, serviceInstanceId);
        
        //return new BasicAWSCredentials(config.getAccessKey(), config.getSecretKey());
      }
    
    private ClientConfiguration createClientConfig() {
        String suffix = String.format("spring_boot/%s", SpringBootVersion.getVersion());
        return new ClientConfigurationFactory().getConfig().withUserAgentSuffix(suffix);
      }

}

package com.parting.dippin.core.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class TestS3Config {

    @Bean
    public BasicAWSCredentials awsCredentialsProvider(){
        return new BasicAWSCredentials("testAccessKey","testSecretKey");
    }

    @Bean
    public AmazonS3 amazonS3TestClient() {
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentialsProvider()))
                .withRegion("ap-northeast-2")
                .build();
    }
}

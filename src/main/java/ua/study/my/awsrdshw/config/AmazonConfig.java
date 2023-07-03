package ua.study.my.awsrdshw.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonConfig {

    @Value("${amazon.accessKey}")
    private String s3AccessKey;
    @Value("${amazon.secretKey}")
    private String s3SecretKey;

    @Value("${amazon.sqs-sns.accessKey}")
    private String snsSqsAccessKey;
    @Value("${amazon.sqs-sns.secretKey}")
    private String snsSqsSecretKey;
    @Value("${amazon.region}")
    private String region;

    @Bean
    public AmazonS3 amazonS3Client() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(s3AccessKey, s3SecretKey);
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();
    }

    @Bean
    public AmazonSQS amazonSQSClient() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(snsSqsAccessKey, snsSqsSecretKey);
        return AmazonSQSClient.builder()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();
    }

    @Bean
    public AmazonSNS amazonSNSClient() {
        BasicAWSCredentials credentials = new BasicAWSCredentials(snsSqsAccessKey, snsSqsSecretKey);
        return AmazonSNSClient.builder()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region)
                .build();
    }
}

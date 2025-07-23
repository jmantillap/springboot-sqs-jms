package work.javiermantilla.sqs.config;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

@Configuration
public class SqsConfig {

    private final String accessKey;
    private final String secretKey;

    public SqsConfig(@Value("${aws.access.key}") String accessKey,
                     @Value("${aws.secret.key}") String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    @Bean
    SQSConnectionFactory getSqsConnectionFactory(){
        ProviderConfiguration providerConfiguration = new ProviderConfiguration();
        return  new SQSConnectionFactory(providerConfiguration, this.SqsClient());
    }

    @Bean
    SqsClient SqsClient() {
        return SqsClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(this.accessKey, this.secretKey)
                ))
                .build();
    }

}

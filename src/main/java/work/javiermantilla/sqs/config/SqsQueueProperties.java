package work.javiermantilla.sqs.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Validated
@NoArgsConstructor
@Data
@Configuration
@ConfigurationProperties(prefix = "aws.sqs.queue")
public class SqsQueueProperties {
    private String name;
    private String url;
}

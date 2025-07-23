package work.javiermantilla.sqs.dto;

import lombok.Data;
import lombok.ToString;

/**
 * Data Transfer Object (DTO) for messages sent to SQS.
 * This class is used to encapsulate the message data that will be sent to the SQS queue.
 */
@ToString
@Data
public class MessageDto {
    private String message;
}
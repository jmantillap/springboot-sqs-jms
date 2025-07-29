package work.javiermantilla.sqs.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import work.javiermantilla.sqs.config.SqsQueueProperties;
import work.javiermantilla.sqs.dto.MessageDto;

import java.util.List;

@Service
public class ProducerService {

    private static final Logger LOG = LoggerFactory.getLogger(ProducerService.class);

     private final JmsTemplate jmsTemplate;
     private final SqsClient sqsClient;
     private final SqsQueueProperties sqsQueueProperties;

     public ProducerService(JmsTemplate jmsTemplate,SqsClient sqsClient, SqsQueueProperties sqsProperties) {
         this.jmsTemplate = jmsTemplate;
         this.sqsClient = sqsClient;
         this.sqsQueueProperties= sqsProperties;

     }

     public void sendMessageTemplate(MessageDto messageDto) {
         LOG.info("Send jms to {} message --> {}", sqsQueueProperties.getName(), messageDto);
         try {
             jmsTemplate.convertAndSend(this.sqsQueueProperties.getName(), this.convertDtoToJson(messageDto));
         } catch (Exception e) {
             LOG.error("Error converting DTO to JSON: {}", e.getMessage(), e);
         }
     }

    public void sendMessageSQS(MessageDto messageDto) {
        LOG.info("Send sqs to {} message --> {}", this.sqsQueueProperties.getName(), messageDto);
        try {
            SendMessageRequest messageRequest = SendMessageRequest.builder()
                    .queueUrl(this.sqsQueueProperties.getUrl())
                    .messageBody(this.convertDtoToJson(messageDto))
                    .build();
            sqsClient.sendMessage(messageRequest);
        } catch (Exception e) {
            LOG.error("Error sending message to SQS: {}", e.getMessage(), e);
        }
    }

    public void receiveMessage() {
        LOG.info("This endpoint is for receiving messages, not implemented yet");
        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                .queueUrl(this.sqsQueueProperties.getUrl())
                .waitTimeSeconds(20)
                .build();
        List<Message> receivedMessages = sqsClient.receiveMessage(receiveMessageRequest).messages();
        StringBuilder messages = new StringBuilder();
        for (Message receivedMessage : receivedMessages) {
            messages.append(receivedMessage.body()).append("\n");
            DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
                    .queueUrl(this.sqsQueueProperties.getUrl())
                    .receiptHandle(receivedMessage.receiptHandle())
                    .build();
            sqsClient.deleteMessage(deleteMessageRequest);
        }
        LOG.info("receive messages {} ", messages);
    }

    private <T>String convertDtoToJson(T dto) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(dto);
    }

}

package work.javiermantilla.sqs.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import work.javiermantilla.sqs.dto.MessageDto;

@Service
public class ProducerService {

    private static final Logger LOG = LoggerFactory.getLogger(ProducerService.class);

    // Aquí puedes implementar la lógica para enviar mensajes a SQS
    // Por ejemplo, utilizando JmsTemplate o cualquier otra librería que prefieras

     private final JmsTemplate jmsTemplate;
     private final String queueName;
     private final SqsClient sqsClient;
     private final String queueUrl;


     public ProducerService(JmsTemplate jmsTemplate,
                            @Value("${aws.sqs.queue.name}") String queueName,
                            SqsClient sqsClient,
                            @Value("${aws.sqs.queue.url}") String queueUrl) {
         this.jmsTemplate = jmsTemplate;
         this.queueName = queueName;
         this.sqsClient = sqsClient;
         this.queueUrl = queueUrl;
     }

     public void sendMessageTemplate(MessageDto messageDto) {
         LOG.info("Send jms to {} message --> {}", queueName, messageDto);
         try {
             jmsTemplate.convertAndSend(this.queueName, this.convertDtoToJson(messageDto));
         } catch (Exception e) {
             LOG.error("Error converting DTO to JSON: {}", e.getMessage(), e);
         }
     }

    public void sendMessageSQS(MessageDto messageDto) {
        LOG.info("Send sqs to {} message --> {}", queueName, messageDto);
        try {
            SendMessageRequest messageRequest = SendMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .messageBody(this.convertDtoToJson(messageDto))
                    .build();
            sqsClient.sendMessage(messageRequest);
        } catch (Exception e) {
            LOG.error("Error sending message to SQS: {}", e.getMessage(), e);
        }
    }

    private <T>String convertDtoToJson(T dto) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(dto);
    }

}

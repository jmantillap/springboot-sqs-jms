package work.javiermantilla.sqs.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import work.javiermantilla.sqs.dto.MessageDto;
import work.javiermantilla.sqs.service.ProducerService;

@RestController
@RequestMapping("/v1/sqs")
@RequiredArgsConstructor
public class MessageController {

    private final ProducerService producerService;

    @PostMapping("/send-jms")
    public ResponseEntity<String> sendMessageJms(@RequestBody MessageDto messageDto) {
        producerService.sendMessageTemplate(messageDto);
        return ResponseEntity.ok("Message sent from JMS template successfully");
    }

    @PostMapping("/send-sqs")
    public ResponseEntity<String> sendMessageSQS(@RequestBody MessageDto messageDto) {
        // Logic to send message to SQS
        producerService.sendMessageSQS(messageDto);
        return ResponseEntity.ok("Message sent from SQS Client successfully");
    }

}

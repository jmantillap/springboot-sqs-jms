package work.javiermantilla.sqs.controller;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConsumerAdapterSqs {

    private static final Logger LOG = LoggerFactory.getLogger(ConsumerAdapterSqs.class);

    @JmsListener(destination = "${aws.sqs.queue.name}")
    public void consumeMessage(String message) {
        LOG.info("Received message: {}", message);
    }

}

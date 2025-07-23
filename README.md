# Firmas desde postman

### Enviar Mensaje por medio de jms usando: JmsTemplate

```bash
$ curl --location 'localhost:8080/v1/sqs/send-jms' \
--header 'Content-Type: application/json' \
--data '{
    "message":"hola mundo jms javier"
}'
```

### Enviar Mensaje por medio de sqsClient usando: SqsClient
```bash
curl --location 'localhost:8080/v1/sqs/send-sqs' \
--header 'Content-Type: application/json' \
--data '{
    "message":"hola mundo sqsclient"
}'
```
### Consumir Mensaje por medio usando: @JmsListener
```bash
    @JmsListener(destination = "${aws.sqs.queue.name}")
    public void consumeMessage(String message) {
        LOG.info("Received message: {}", message);
    }
```

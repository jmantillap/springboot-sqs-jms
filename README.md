# Firmas desde postman

### Enviar Mensaje por medio de jms

```bash
$ curl --location 'localhost:8080/v1/sqs/send-jms' \
--header 'Content-Type: application/json' \
--data '{
    "message":"hola mundo jms javier"
}'
```

### Enviar Mensaje por medio de sqsClient
```bash
curl --location 'localhost:8080/v1/sqs/send-sqs' \
--header 'Content-Type: application/json' \
--data '{
    "message":"hola mundo sqsclient"
}'
```

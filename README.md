# URL CHECKER

### Run with docker-compose

```
./mvnw clean install

docker-compose up
```

Using https://github.com/eficode/wait-for for busy waiting.



## Requirements

Validate URLs posted on RabbitMQ using regex, check whitelist on MySQL, post result back to RabbitMQ
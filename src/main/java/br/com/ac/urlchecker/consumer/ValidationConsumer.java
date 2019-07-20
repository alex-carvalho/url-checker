package br.com.ac.urlchecker.consumer;

import br.com.ac.urlchecker.dto.ValidationItem;
import br.com.ac.urlchecker.service.ValidationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @author Aléx Carvalho
 */
@Component
public class ValidationConsumer {

    private final Logger logger = LoggerFactory.getLogger(ValidationConsumer.class);

    private final ValidationService validationService;

    public ValidationConsumer(ValidationService validationService) {
        this.validationService = validationService;
    }


    @RabbitListener(queues = "${amqp.validation.queue}", concurrency = "${amqp.validation.number-consumers}")
    public void receive(Message message) {
        try {
            String body = new String(message.getBody(), StandardCharsets.UTF_8);
            logger.info("Validation receive message: {}", body);
            ValidationItem validationItem = new ObjectMapper().readValue(body, ValidationItem.class);
            validationService.validate(validationItem);
        } catch (Exception e) {
            logger.error("Error on receive validation message.", e);
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }
}

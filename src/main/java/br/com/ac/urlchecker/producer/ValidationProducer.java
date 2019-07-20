package br.com.ac.urlchecker.producer;

import br.com.ac.urlchecker.dto.ValidationResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author Aléx Carvalho
 */
@Component
public class ValidationProducer {

    private final Logger logger = LoggerFactory.getLogger(ValidationProducer.class);
    private final RabbitTemplate rabbitTemplate;

    @Value("${amqp.response.exchange}")
    private String exchange;
    @Value("${amqp.response.routing-key}")
    private String routingKey;

    public ValidationProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @EventListener
    public void send(ValidationResponse response) {
        try {
            logger.info("Send message: {}", response);
            rabbitTemplate.convertAndSend(exchange, routingKey, new ObjectMapper().writeValueAsBytes(response));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

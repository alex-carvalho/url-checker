package br.com.ac.urlchecker.consumer;

import br.com.ac.urlchecker.entity.WhitelistItem;
import br.com.ac.urlchecker.service.WhitelistService;
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
public class InsertionConsumer {

    private final Logger logger = LoggerFactory.getLogger(InsertionConsumer.class);

    private final WhitelistService whitelistService;

    public InsertionConsumer(WhitelistService whitelistService) {
        this.whitelistService = whitelistService;
    }

    @RabbitListener(queues = "${amqp.insertion.queue}")
    public void receive(Message message) {
        try {
            String body = new String(message.getBody(), StandardCharsets.UTF_8);
            logger.info("Insertion receive message: {}", body);
            WhitelistItem whitelistItem = new ObjectMapper().readValue(body, WhitelistItem.class);
            whitelistService.insert(whitelistItem);
        } catch (Exception e) {
            logger.error("Error on receive insertion message.", e);
            throw new AmqpRejectAndDontRequeueException(e);
        }
    }
}

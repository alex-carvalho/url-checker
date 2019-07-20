package br.com.ac.urlchecker.config;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Aléx Carvalho
 * <p>
 * Create queue and exchange if not exists
 */
@Configuration
public class RabbitConfiguration {


    @Value("${amqp.insertion.queue}")
    private String insertionQueue;

    @Value("${amqp.validation.queue}")
    private String validationQueue;

    @Value("${amqp.response.exchange}")
    private String responseExchange;

    @Bean
    public Queue insertionQueue() {
        return new Queue(insertionQueue);
    }

    @Bean
    public Queue validationQueue() {
        return new Queue(validationQueue);
    }

    @Bean
    public Exchange responseExchange() {
        return new TopicExchange(responseExchange);
    }

}

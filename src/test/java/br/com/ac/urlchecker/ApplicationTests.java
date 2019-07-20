package br.com.ac.urlchecker;

import br.com.ac.urlchecker.dto.ValidationResponse;
import br.com.ac.urlchecker.entity.WhitelistItem;
import br.com.ac.urlchecker.repository.WhitelistRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.util.function.Consumer;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class ApplicationTests {

    private static EmbeddedInMemoryQpidBroker broker;

    @Autowired
    private WhitelistRepository whitelistRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private CachingConnectionFactory connectionFactory;

    @BeforeAll
    static void beforeAll() {
        broker = new EmbeddedInMemoryQpidBroker();
        broker.start();

    }

    @AfterAll
    static void after() {
        broker.shutdown();
    }

    @Test
    void testInsertClient() {
        sendInsertionMessageAndAwait();

        WhitelistItem whitelistItem = whitelistRepository.findById(1L).orElse(null);

        Assertions.assertAll(
                () -> Assertions.assertNotNull(whitelistItem),
                () -> Assertions.assertEquals("1", whitelistItem.getClient()),
                () -> Assertions.assertEquals("google", whitelistItem.getRegex())
        );
    }

    @Test
    void testValidation() throws IOException {
        StringBuilder validationResponseMessage = new StringBuilder();
        createQueueToResponseValidationAndAddListner(message -> {
            validationResponseMessage.append(new String(message.getBody(), Charsets.UTF_8));
        });

        sendInsertionMessageAndAwait();

        sendValidationMessageAndAwait();

        ValidationResponse validationResponse = new ObjectMapper().readValue(validationResponseMessage.toString(), ValidationResponse.class);

        Assertions.assertAll(
                () -> Assertions.assertEquals(Integer.valueOf(1), validationResponse.getCorrelationId()),
                () -> Assertions.assertEquals("google", validationResponse.getRegex()),
                () -> Assertions.assertTrue(validationResponse.isMatch())
        );
    }

    private void createQueueToResponseValidationAndAddListner(Consumer<Message> messageConsumer) {
        final RabbitAdmin admin = new RabbitAdmin(connectionFactory);
        final Queue queue = new Queue("response.queue", false);
        admin.declareQueue(queue);
        final TopicExchange exchange = new TopicExchange("response.exchange");
        admin.declareBinding(BindingBuilder.bind(queue).to(exchange).with("#"));

        SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer(connectionFactory);
        listenerContainer.addQueueNames("response.queue");
        listenerContainer.setMessageListener(messageConsumer::accept);
        listenerContainer.start();
    }

    private void sendInsertionMessageAndAwait() {
        rabbitTemplate.convertAndSend("insertion.queue", "{\"client\":\"1\", \"regex\": \"google\"}");

        sleep(500);
    }

    private void sendValidationMessageAndAwait() {
        rabbitTemplate.convertAndSend("validation.queue", "{\"client\": \"1\", \"url\": \"http://google.com\", \"correlationId\": 1}");

        sleep(1000);
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

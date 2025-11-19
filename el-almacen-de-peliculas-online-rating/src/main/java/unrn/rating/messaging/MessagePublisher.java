package unrn.rating.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessagePublisher {

    private static final Logger log = LoggerFactory.getLogger(MessagePublisher.class);
    private final RabbitTemplate rabbitTemplate;
    private final TopicExchange exchange;

    public MessagePublisher(RabbitTemplate rabbitTemplate, TopicExchange peliculasExchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = peliculasExchange;
    }

    public <K, T> void publish(Event<K, T> event) {
        log.info("Publicando evento al exchange '{}' con routing key '{}': {}",
                exchange.getName(), event.routingKey(), event.getData());
        rabbitTemplate.convertAndSend(exchange.getName(), event.routingKey(), event);
        log.info("Evento publicado exitosamente");
    }
}

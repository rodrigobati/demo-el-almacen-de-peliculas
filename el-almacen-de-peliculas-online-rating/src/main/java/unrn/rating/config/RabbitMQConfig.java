package unrn.rating.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    /**
     * Exchange tipo 'topic' para publicar eventos de cambios en ratings.
     * Usa el mismo exchange que la vertical Catálogo ('exchange_videocloud00')
     * pero con tipo 'topic' para soportar routing keys dinámicas como
     * 'RatingActualizadoEvent.CREATE'
     */
    @Bean
    public TopicExchange peliculasExchange(
            @Value("${rating.rabbitmq.exchange:exchange_videocloud00}") String exchangeName) {
        return new TopicExchange(exchangeName);
    }

    /**
     * Convierte los mensajes a JSON para serialización/deserialización automática
     */
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}

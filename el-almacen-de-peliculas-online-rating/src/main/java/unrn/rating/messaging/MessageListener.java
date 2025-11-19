package unrn.rating.messaging;

import org.springframework.stereotype.Component;

@Component
public class MessageListener {

    // TODO: placeholder to handle events from other services in the future, e.g.,
    // pelicula.deleted
    // Currently DISABLED because Rating service only publishes events, doesn't
    // consume them

    // @RabbitListener(queues = "${rating.rabbitmq.queue:rating.queue}")
    // public void onMessage(Event<?, ?> event) {
    // // For future use: react to other events (e.g., delete ratings when a movie
    // is deleted)
    // }
}

package unrn.rating.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import unrn.rating.model.Rating;
import unrn.rating.repository.RatingRepository;
import unrn.rating.messaging.Event;
import unrn.rating.messaging.EventType;
import unrn.rating.messaging.MessagePublisher;
import unrn.rating.messaging.RatingActualizadoEvent;

import java.util.List;

@Service
public class RatingService {

    private final RatingRepository repository;
    private final MessagePublisher publisher;

    public RatingService(RatingRepository repository, MessagePublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    @Transactional
    public Rating createRating(Rating rating) {
        Rating saved = repository.save(rating);

        // Calcular datos agregados para el catálogo
        double promedio = promedioPorPelicula(saved.peliculaId());
        long totalRatings = repository.countByPeliculaId(saved.peliculaId());

        // Crear evento compatible con formato que espera Catálogo
        var datosAgregados = new RatingActualizadoEvent(
                saved.peliculaId(),
                (int) Math.round(promedio),
                totalRatings);

        // Routing key se genera automáticamente: "RatingActualizadoEvent.CREATE"
        Event<String, RatingActualizadoEvent> event = new Event<>(
                EventType.CREATE,
                saved.peliculaId().toString(),
                datosAgregados);
        
        publisher.publish(event);
        return saved;
    }

    public List<Rating> ratingsPorPelicula(Long peliculaId) {
        return repository.findByPeliculaId(peliculaId);
    }

    public List<Rating> ratingsPorUsuario(String usuarioId) {
        return repository.findByUsuarioId(usuarioId);
    }

    public double promedioPorPelicula(Long peliculaId) {
        Double avg = repository.promedioPorPelicula(peliculaId);
        return avg == null ? 0.0 : avg;
    }
}

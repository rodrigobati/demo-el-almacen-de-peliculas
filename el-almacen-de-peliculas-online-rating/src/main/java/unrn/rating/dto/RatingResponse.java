package unrn.rating.dto;

import java.time.LocalDateTime;

import unrn.rating.model.Rating;

public record RatingResponse(
        Long id,
        Long peliculaId,
        String usuarioId,
        int valor,
        String comentario,
        LocalDateTime fechaCreacion) {

    public static RatingResponse desde(Rating rating) {
        return new RatingResponse(
                rating.id(),
                rating.peliculaId(),
                rating.usuarioId(),
                rating.valor(),
                rating.comentario(),
                rating.fechaCreacion());
    }
}

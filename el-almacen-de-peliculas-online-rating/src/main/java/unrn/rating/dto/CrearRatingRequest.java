package unrn.rating.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearRatingRequest(
        @NotNull Long peliculaId,
        @NotBlank String usuarioId,
        @Min(1) @Max(10) int valor,
        String comentario) {
}

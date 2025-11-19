package unrn.rating.api;

import java.time.LocalDateTime;

public class RatingResponseDto {
    public Long id;
    public Long peliculaId;
    public String usuarioId;
    public int valor;
    public String comentario;
    public LocalDateTime fechaCreacion;

    public RatingResponseDto() {
    }
}

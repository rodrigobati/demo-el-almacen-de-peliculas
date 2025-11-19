package unrn.rating.api;

/**
 * DTO para crear un rating.
 */
public class RatingRequestDto {
    public Long peliculaId;
    public int valor;
    public String comentario;
    public String usuarioId; // ID del usuario autenticado

    public RatingRequestDto() {
    }
}

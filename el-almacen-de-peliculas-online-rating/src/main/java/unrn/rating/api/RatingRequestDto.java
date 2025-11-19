package unrn.rating.api;

/**
 * DTO para crear un rating.
 * El usuarioId se extrae del JWT, no del request.
 */
public class RatingRequestDto {
    public Long peliculaId;
    public int valor;
    public String comentario;

    public RatingRequestDto() {
    }
}

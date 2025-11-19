package unrn.rating.messaging;

/**
 * DTO compatible con el formato que espera la vertical Catálogo.
 * Representa una película con su rating actualizado.
 */
public class RatingActualizadoEvent {

    private Long id;
    private int rating;
    private long totalRatings;

    // Constructor sin argumentos requerido para deserialización JSON
    public RatingActualizadoEvent() {
    }

    public RatingActualizadoEvent(Long id, int rating, long totalRatings) {
        this.id = id;
        this.rating = rating;
        this.totalRatings = totalRatings;
    }

    // Getters siguiendo el formato que espera Catálogo
    public Long id() {
        return id;
    }

    public int rating() {
        return rating;
    }

    public long totalRatings() {
        return totalRatings;
    }

    // Getters para Jackson (convención JavaBean)
    public Long getId() {
        return id;
    }

    public int getRating() {
        return rating;
    }

    public long getTotalRatings() {
        return totalRatings;
    }

    // Setters para deserialización JSON
    public void setId(Long id) {
        this.id = id;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setTotalRatings(long totalRatings) {
        this.totalRatings = totalRatings;
    }

    @Override
    public String toString() {
        return "RatingActualizadoEvent{" +
                "id=" + id +
                ", rating=" + rating +
                ", totalRatings=" + totalRatings +
                '}';
    }
}

package unrn.rating.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public final class PeliculaId {

    static final String ERROR_PELICULA_OBLIGATORIA = "El id de la película es obligatorio";

    @Column(name = "pelicula_id", nullable = false)
    private Long value;

    protected PeliculaId() {
        // Requerido por JPA
    }

    private PeliculaId(Long value) {
        if (value == null) {
            throw new RuntimeException(ERROR_PELICULA_OBLIGATORIA);
        }
        this.value = value;
    }

    public static PeliculaId de(Long value) {
        return new PeliculaId(value);
    }

    public Long value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof PeliculaId that))
            return false;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}

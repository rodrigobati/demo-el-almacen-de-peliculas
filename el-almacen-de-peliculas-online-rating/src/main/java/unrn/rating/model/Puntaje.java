package unrn.rating.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public final class Puntaje {

    static final String ERROR_VALOR_FUERA_DE_RANGO = "El puntaje debe estar entre 1 y 10";

    @Column(name = "valor", nullable = false)
    private Integer value;

    protected Puntaje() {
        // Requerido por JPA
    }

    private Puntaje(Integer value) {
        if (value == null || value < 1 || value > 10) {
            throw new RuntimeException(ERROR_VALOR_FUERA_DE_RANGO);
        }
        this.value = value;
    }

    public static Puntaje de(int value) {
        return new Puntaje(value);
    }

    public int value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Puntaje puntaje))
            return false;
        return Objects.equals(value, puntaje.value);
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

package unrn.rating.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public final class UsuarioId {

    static final String ERROR_USUARIO_OBLIGATORIO = "El id del usuario es obligatorio";

    @Column(name = "usuario_id", nullable = false, length = 100)
    private String value;

    protected UsuarioId() {
        // Requerido por JPA
    }

    private UsuarioId(String value) {
        if (value == null || value.isBlank()) {
            throw new RuntimeException(ERROR_USUARIO_OBLIGATORIO);
        }
        this.value = value;
    }

    public static UsuarioId de(String value) {
        return new UsuarioId(value);
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof UsuarioId that))
            return false;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}

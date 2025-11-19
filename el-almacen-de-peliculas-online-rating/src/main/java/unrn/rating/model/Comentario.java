package unrn.rating.model;

public class Comentario {

    static final String ERROR_COMENTARIO_VACIO = "El comentario no puede estar vacío";

    private final String value;

    public Comentario(String value) {
        assertComentarioNoVacio(value);
        this.value = value;
    }

    /**
     * Si el comentario viene null, devolvemos null (comentario opcional).
     */
    public static Comentario desdeTextoOpcional(String texto) {
        if (texto == null) {
            return null;
        }
        var limpio = texto.trim();
        return new Comentario(limpio);
    }

    private void assertComentarioNoVacio(String value) {
        if (value == null || value.isBlank()) {
            throw new RuntimeException(ERROR_COMENTARIO_VACIO);
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Comentario that = (Comentario) o;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

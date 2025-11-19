package unrn.rating.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RatingTest {

    @Test
    @DisplayName("Valor fuera de rango lanza excepción")
    void valor_fuera_de_rango_lanza_excepcion() {
        var ex = assertThrows(RuntimeException.class, () -> Rating.crear(1L, "user1", 11, null));
        assertEquals(Puntaje.ERROR_VALOR_FUERA_DE_RANGO, ex.getMessage());
    }

    @Test
    @DisplayName("Comentario vacío cuando se informa lanza excepción")
    void comentario_vacio_informa_lanza_excepcion() {
        var ex = assertThrows(RuntimeException.class, () -> Rating.crear(1L, "user1", 5, "   "));
        assertEquals(Rating.ERROR_COMENTARIO_VACIO, ex.getMessage());
    }

    @Test
    @DisplayName("Pelicula nula lanza excepción")
    void pelicula_nula_lanza_excepcion() {
        var ex = assertThrows(RuntimeException.class, () -> Rating.crear(null, "user1", 5, null));
        assertEquals(Rating.ERROR_PELICULA_OBLIGATORIA, ex.getMessage());
    }

    @Test
    @DisplayName("Usuario nulo lanza excepción")
    void usuario_nulo_lanza_excepcion() {
        var ex = assertThrows(RuntimeException.class, () -> Rating.crear(1L, null, 5, null));
        assertEquals(Rating.ERROR_USUARIO_OBLIGATORIO, ex.getMessage());
    }
}

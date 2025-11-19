package unrn.rating.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ComentarioTest {

    @Test
    @DisplayName("Comentario válido se crea correctamente")
    void comentario_valido_se_crea_correctamente() {
        // Setup
        String texto = "Excelente película";

        // Ejercitación
        Comentario comentario = new Comentario(texto);

        // Verificación
        assertEquals(texto, comentario.toString(), "El comentario debería contener el texto proporcionado");
    }

    @Test
    @DisplayName("Comentario vacío lanza excepción")
    void comentario_vacio_lanza_excepcion() {
        // Setup & Ejercitación & Verificación
        var ex = assertThrows(RuntimeException.class, () -> new Comentario("   "));
        assertEquals(Comentario.ERROR_COMENTARIO_VACIO, ex.getMessage(),
                "Debería lanzar excepción con mensaje de comentario vacío");
    }

    @Test
    @DisplayName("Comentario nulo lanza excepción")
    void comentario_nulo_lanza_excepcion() {
        // Setup & Ejercitación & Verificación
        var ex = assertThrows(RuntimeException.class, () -> new Comentario(null));
        assertEquals(Comentario.ERROR_COMENTARIO_VACIO, ex.getMessage(),
                "Debería lanzar excepción con mensaje de comentario vacío");
    }

    @Test
    @DisplayName("DesdeTextoOpcional con null devuelve null")
    void desdeTextoOpcional_con_null_devuelve_null() {
        // Setup & Ejercitación
        Comentario comentario = Comentario.desdeTextoOpcional(null);

        // Verificación
        assertNull(comentario, "Debería devolver null cuando el texto es null");
    }

    @Test
    @DisplayName("DesdeTextoOpcional con texto válido crea comentario")
    void desdeTextoOpcional_con_texto_valido_crea_comentario() {
        // Setup
        String texto = "  Buena trama  ";

        // Ejercitación
        Comentario comentario = Comentario.desdeTextoOpcional(texto);

        // Verificación
        assertNotNull(comentario, "Debería crear un comentario válido");
        assertEquals("Buena trama", comentario.toString(), "Debería eliminar espacios al inicio y final");
    }
}

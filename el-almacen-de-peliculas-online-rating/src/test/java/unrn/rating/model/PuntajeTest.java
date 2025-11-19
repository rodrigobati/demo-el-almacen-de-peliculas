package unrn.rating.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PuntajeTest {

    @Test
    @DisplayName("Puntaje válido 1 se crea correctamente")
    void puntaje_valido_1_se_crea_correctamente() {
        // Setup & Ejercitación
        Puntaje puntaje = Puntaje.de(1);

        // Verificación
        assertEquals(1, puntaje.value(), "El puntaje debería ser 1");
    }

    @Test
    @DisplayName("Puntaje válido 10 se crea correctamente")
    void puntaje_valido_10_se_crea_correctamente() {
        // Setup & Ejercitación
        Puntaje puntaje = Puntaje.de(10);

        // Verificación
        assertEquals(10, puntaje.value(), "El puntaje debería ser 10");
    }

    @Test
    @DisplayName("Puntaje menor a 1 lanza excepción")
    void puntaje_menor_a_1_lanza_excepcion() {
        // Setup & Ejercitación & Verificación
        var ex = assertThrows(RuntimeException.class, () -> Puntaje.de(0));
        assertEquals(Puntaje.ERROR_VALOR_FUERA_DE_RANGO, ex.getMessage(),
                "Debería lanzar excepción indicando valor fuera de rango");
    }

    @Test
    @DisplayName("Puntaje mayor a 10 lanza excepción")
    void puntaje_mayor_a_10_lanza_excepcion() {
        // Setup & Ejercitación & Verificación
        var ex = assertThrows(RuntimeException.class, () -> Puntaje.de(11));
        assertEquals(Puntaje.ERROR_VALOR_FUERA_DE_RANGO, ex.getMessage(),
                "Debería lanzar excepción indicando valor fuera de rango");
    }

    @Test
    @DisplayName("Puntaje con valor medio se crea correctamente")
    void puntaje_valor_medio_se_crea_correctamente() {
        // Setup & Ejercitación
        Puntaje puntaje = Puntaje.de(5);

        // Verificación
        assertEquals(5, puntaje.value(), "El puntaje debería ser 5");
    }
}

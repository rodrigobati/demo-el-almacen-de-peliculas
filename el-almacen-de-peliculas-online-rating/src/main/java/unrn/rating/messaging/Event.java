package unrn.rating.messaging;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Event<K, T> {
    private EventType type;
    private K key;
    private T data;
    private String routingKey;

    static final String ERROR_TIPO_OBLIGATORIO = "El tipo del evento es obligatorio.";
    static final String ERROR_ROUTINGKEY_OBLIGATORIO = "La clave de enrutamiento es obligatoria.";

    // Constructor sin routing key: se genera automáticamente
    public Event(EventType type, K key, T data) {
        this.type = type;
        this.key = key;
        this.data = data;
        this.routingKey = null; // Se generará dinámicamente

        assertTipoPresente();
    }

    // Constructor con routing key explícita (para casos especiales)
    public Event(EventType type, K key, T data, String routingKey) {
        this.type = type;
        this.key = key;
        this.data = data;
        this.routingKey = routingKey == null ? null : routingKey.trim();

        assertTipoPresente();
        assertRoutingKeyPresente();
    }

    private void assertTipoPresente() {
        if (this.type == null) {
            throw new RuntimeException(ERROR_TIPO_OBLIGATORIO);
        }
    }

    private void assertRoutingKeyPresente() {
        if (this.routingKey == null || this.routingKey.isBlank()) {
            throw new RuntimeException(ERROR_ROUTINGKEY_OBLIGATORIO);
        }
    }

    // Accesores con intención (no getters tontos)
    public EventType type() {
        return type;
    }

    public K key() {
        return key;
    }

    public T data() {
        return data;
    }

    public String routingKey() {
        // Si se proporcionó una routing key explícita, usarla
        if (routingKey != null && !routingKey.isBlank()) {
            return routingKey;
        }
        // Caso contrario, generar automáticamente: "NombreClase.TIPO_EVENTO"
        // Ej: "RatingActualizadoEvent.CREATE"
        return data.getClass().getSimpleName() + "." + type;
    }

    // Getters para Jackson
    public EventType getType() {
        return type();
    }

    public K getKey() {
        return key();
    }

    public T getData() {
        return data();
    }

    public String getRoutingKey() {
        return routingKey();
    }
}

package unrn.rating.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import unrn.rating.model.Rating;
import unrn.rating.service.RatingService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    private final RatingService service;

    public RatingController(RatingService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<RatingResponseDto> crear(
            @RequestBody RatingRequestDto req,
            @AuthenticationPrincipal(errorOnInvalidType = false) Jwt jwt) {
        // Extraer el userId del JWT (claim "sub" contiene el ID de usuario de Keycloak)
        // Para testing sin auth, usar un ID fijo si jwt es null
        String usuarioId = (jwt != null) ? jwt.getSubject() : "test-user-123";
        Rating rating = RatingMapper.toModel(req, usuarioId);
        Rating saved = service.createRating(rating);
        return ResponseEntity.ok(RatingMapper.toDto(saved));
    }

    @GetMapping("/pelicula/{peliculaId}")
    public ResponseEntity<List<RatingResponseDto>> porPelicula(@PathVariable Long peliculaId) {
        var list = service.ratingsPorPelicula(peliculaId).stream().map(RatingMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/pelicula/{peliculaId}/promedio")
    public ResponseEntity<Double> promedio(@PathVariable Long peliculaId) {
        return ResponseEntity.ok(service.promedioPorPelicula(peliculaId));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<RatingResponseDto>> porUsuario(
            @PathVariable String usuarioId,
            @AuthenticationPrincipal Jwt jwt) {
        // Opcional: validar que el usuario solo pueda ver sus propios ratings
        // String usuarioIdToken = jwt.getSubject();
        // if (!usuarioIdToken.equals(usuarioId)) throw new
        // AccessDeniedException("...");

        var list = service.ratingsPorUsuario(usuarioId).stream().map(RatingMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.ratingsPorPelicula(id); // placeholder to keep behavior clear; implement delete if desired
        return ResponseEntity.noContent().build();
    }
}

package unrn.rating.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import unrn.rating.model.Rating;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByPeliculaId(Long peliculaId);

    List<Rating> findByUsuarioId(String usuarioId);

    @Query("SELECT AVG(r.puntaje.value) FROM Rating r WHERE r.peliculaId = :peliculaId")
    Double promedioPorPelicula(@Param("peliculaId") Long peliculaId);

    long countByPeliculaId(Long peliculaId);
}

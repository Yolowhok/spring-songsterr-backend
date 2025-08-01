package ru.imit.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.imit.service.dto.CompositionDTO;
import ru.imit.service.models.Composition;

@Repository
public interface CompositionRepository extends JpaRepository<Composition, Long> {
    @Query(value = "SELECT id, band, title, created_at, updated_at FROM Composition c WHERE c.id = :id",
            nativeQuery = true)
    Composition getOnlyCompositionById(@Param("id") Long id);


}



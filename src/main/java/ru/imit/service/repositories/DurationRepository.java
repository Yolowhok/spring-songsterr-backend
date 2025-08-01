package ru.imit.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.imit.service.models.Duration;

@Repository
public interface DurationRepository extends JpaRepository<Duration, Long> {

    @Query(value = "SELECT * FROM duration WHERE name = :type LIMIT 1", nativeQuery = true)
    Duration getDurationByName(@Param("type") String type);
}

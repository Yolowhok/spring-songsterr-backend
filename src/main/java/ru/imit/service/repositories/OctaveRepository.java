package ru.imit.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.imit.service.enums.OctaveType;
import ru.imit.service.models.Octave;

@Repository
public interface OctaveRepository extends JpaRepository<Octave,Long> {
    @Query(name = "SELECT * FROM octave WHERE name = :type LIMIT 1", nativeQuery = true)
    Octave getOctaveByName(@Param("type") String type);
}

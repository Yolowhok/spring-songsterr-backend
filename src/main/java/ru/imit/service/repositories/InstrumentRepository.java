package ru.imit.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.imit.service.enums.InstrumentType;
import ru.imit.service.models.Instrument;

@Repository
public interface InstrumentRepository extends JpaRepository<Instrument, Integer> {
    @Query(value = "SELECT * FROM instrument WHERE name = :name LIMIT 1", nativeQuery = true)
    Instrument findByName(@Param("name") String name);
}
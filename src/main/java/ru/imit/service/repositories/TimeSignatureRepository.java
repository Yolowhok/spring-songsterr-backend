package ru.imit.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.imit.service.models.TimeSignature;

@Repository
public interface TimeSignatureRepository extends JpaRepository<TimeSignature, Long> {
}

package ru.imit.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.imit.service.models.Tuning;
import ru.imit.service.models.TuningString;

@Repository
public interface TuningStringRepository extends JpaRepository<TuningString, Integer> {
}

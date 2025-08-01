package ru.imit.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.imit.service.models.BeatNote;

@Repository
public interface BeatNoteRepository extends JpaRepository<BeatNote, Long> {
}

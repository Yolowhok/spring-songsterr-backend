package ru.imit.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.imit.service.enums.NoteType;
import ru.imit.service.models.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

    @Query(value = "SELECT * FROM note WHERE name = :type LIMIT 1", nativeQuery = true)
    Note getNoteByName(@Param("type") String type);

}

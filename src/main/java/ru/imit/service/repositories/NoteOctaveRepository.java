package ru.imit.service.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.imit.service.models.Note;
import ru.imit.service.models.NoteOctave;

import java.util.List;

@Repository
public interface NoteOctaveRepository extends JpaRepository<NoteOctave, Long> {

    @Query(value = "SELECT note_octave.id, note.id AS note_id, octave.id AS octave_id, " +
            "note.name, octave.value, octave.name " +
            "FROM note_octave " +
            "INNER JOIN note ON note_octave.note_id = note.id " +
            "INNER JOIN octave ON note_octave.octave_id = octave.id " +
            "ORDER BY octave_id, note_id", nativeQuery = true)
    List<NoteOctave> getNoteOctavesOrdered();

}

package ru.imit.service.Tools;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.imit.service.enums.DurationType;
import ru.imit.service.enums.InstrumentType;
import ru.imit.service.models.*;
import ru.imit.service.repositories.NoteOctaveRepository;
import ru.imit.service.repositories.NoteRepository;
import ru.imit.service.repositories.OctaveRepository;
import ru.imit.service.services.*;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CreateLinesSQL {
    @Autowired
    NoteOctaveRepository noteOctaveRepository;
    @Autowired
    NoteRepository noteRepository;
    @Autowired
    OctaveRepository octaveRepository;
    @Autowired
    DurationService durationService;
    @Autowired
    TimeSignatureService timeSignatureService;
    @Autowired
    InstrumentService instrumentService;
    @Autowired
    NotesheetService notesheetService;
    @Autowired
    CompositionService compositionService;
    public Optional<List<NoteOctave>> update() {
        try {
            List<Note> notes = noteRepository.findAll();
            List<Octave> octaves = octaveRepository.findAll();
            List<NoteOctave> noteOctaves = new ArrayList<>();
            for (Note note :
                    notes) {
                for (Octave octave :
                        octaves) {
                    NoteOctave noteOctave = NoteOctave.builder()
                            .id(null)
                            .note(note)
                            .octave(octave)
                            .build();
                    noteOctaves.add(noteOctave);
                    noteOctaveRepository.save(noteOctave);
                }
            }
            return Optional.ofNullable(noteOctaves);
        } catch (DataIntegrityViolationException | EntityNotFoundException e) {
            return Optional.empty();
        }
    }
    public Notesheet create() {
        List<Bar> bars = new ArrayList<>();
        List<Beat> beats = new ArrayList<>();
        List<BeatNote> beatNotes = new ArrayList<>();
        List<NoteOctave> noteOctaves = new ArrayList<>();

        Composition composition = Composition.builder()
                .id(null)
                .band("Metallica")
                .title("Nothing else matter")
                .build();
        composition = compositionService.saveComposition(composition).get();

        Notesheet notesheet = Notesheet.builder()
                .id(null)
                .composition(composition)
                .instrument(instrumentService.getInstrumentByName(InstrumentType.GUITAR).get())
                .bars(bars)
                .build();
        bars.add(Bar.builder()
                .id(null)
                .notesheet(notesheet)
                .beats(beats)
                .orderIndex(1)
                .timeSignature(timeSignatureService.getTimeSignatureById(1L).get())
                .tempInBpm(120)
                .build());
        bars.get(0).getBeats().add(Beat.builder()
                .id(null)
                .bar(bars.get(0))
                .duration(durationService.getDurationByName(DurationType.QUARTER).get())
                .beatNotes(beatNotes)
                .build());
        bars.get(0).getBeats().get(0).getBeatNotes().add(BeatNote.builder()
                .id(null)
                .beat(bars.get(0).getBeats().get(0))
                .noteOctave(noteOctaveRepository.getOne(7L))
                .build());

        return notesheetService.saveNotesheet(notesheet).get();

    }

}

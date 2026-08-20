package ru.imit.service.composition.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Transactional;
import ru.imit.service.dto.NotesheetCreateDTO;
import ru.imit.service.dto.NotesheetDTO;
import ru.imit.service.models.*;
import ru.imit.service.repositories.CompositionRepository;
import ru.imit.service.repositories.NotesheetRepository;
import ru.imit.service.services.TimeSignatureService;

import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NotesheetUseCase {

    @Autowired
    private NotesheetRepository notesheetRepository;
    @Autowired
    private CompositionRepository compositionRepository;
    @Autowired
    private TimeSignatureService timeSignatureService;

    public Optional<Notesheet> getNotesheetById(Long id) {
        return notesheetRepository.findById(id);
    }

    public Optional<List<NotesheetDTO>> listNotesheetsByComposition(Long compositionId) {
        List<Notesheet> sheets = notesheetRepository.getNotesheetsById(compositionId);
        if (sheets == null || sheets.isEmpty()) return Optional.empty();
        List<NotesheetDTO> dtos = new ArrayList<>();
        for (Notesheet ns : sheets) {
            dtos.add(NotesheetDTO.builder()
                    .id(ns.getId())
                    .tuning(ns.getTuning())
                    .instrument(ns.getInstrument())
                    .build());
        }
        return Optional.of(dtos);
    }

    @Transactional
    public Optional<Notesheet> updateNotesheet(Notesheet notesheet) {
        notesheet.setComposition(notesheetRepository.getReferenceById(notesheet.getId()).getComposition());
        for (Bar bar : notesheet.getBars()) {
            bar.setNotesheet(notesheet);
            for (Beat beat : bar.getBeats()) {
                beat.setBar(bar);
                for (BeatNote bn : beat.getBeatNotes()) {
                    bn.setBeat(beat);
                }
            }
        }
        return Optional.of(notesheetRepository.saveAndFlush(notesheet));
    }

    public void deleteNotesheet(Long id) {
        if (!notesheetRepository.existsById(id)) {
            throw new EntityNotFoundException("Notesheet with id " + id + " not found");
        }
        notesheetRepository.deleteById(id);
    }

    @Transactional
    public Optional<Notesheet> createNotesheet(NotesheetCreateDTO dto) {
        try {
            Composition composition = compositionRepository.getReferenceById(dto.getCompositionID());
            Notesheet notesheet = Notesheet.builder()
                    .tuning(dto.getTuning())
                    .instrument(dto.getInstrument())
                    .composition(composition)
                    .build();
            TimeSignature ts = timeSignatureService.getTimeSignatureById(3L).orElseThrow();
            Bar bar = Bar.builder()
                    .notesheet(notesheet)
                    .orderIndex(1)
                    .tempInBpm(120)
                    .timeSignature(ts)
                    .build();
            notesheet.setBars(List.of(bar));
            return Optional.of(notesheetRepository.save(notesheet));
        } catch (TransactionException e) {
            return Optional.empty();
        }
    }
}

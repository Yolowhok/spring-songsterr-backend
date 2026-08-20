package ru.imit.service.composition.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.imit.service.composition.api.dto.CompositionSaveRequest;
import ru.imit.service.dto.CompositionDTO;
import ru.imit.service.models.*;
import ru.imit.service.repositories.CompositionRepository;
import ru.imit.service.services.InstrumentService;
import ru.imit.service.services.TimeSignatureService;
import ru.imit.service.services.TuningService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Application use-cases for the composition bounded context.
 * Replaces the old CompositionService for all composition-level operations.
 * Old CompositionService is kept for backward compatibility with catalog and tools code.
 */
@Service
public class CompositionUseCase {

    @Autowired
    private CompositionRepository compositionRepository;
    @Autowired
    private InstrumentService instrumentService;
    @Autowired
    private TuningService tuningService;
    @Autowired
    private TimeSignatureService timeSignatureService;

    public Optional<CompositionDTO> getCompositionSummaryById(Long id) {
        return compositionRepository.findById(id).map(c -> CompositionDTO.builder()
                .id(c.getId())
                .title(c.getTitle())
                .band(c.getBand())
                .createdAt(c.getCreatedAt())
                .updatedAt(c.getUpdatedAt())
                .build());
    }

    public Optional<Composition> getFullCompositionById(Long id) {
        return compositionRepository.findById(id);
    }

    public Optional<List<CompositionDTO>> listCompositions() {
        List<Composition> all = compositionRepository.findAll();
        if (all.isEmpty()) return Optional.empty();
        List<CompositionDTO> result = new ArrayList<>();
        for (Composition c : all) {
            result.add(CompositionDTO.builder()
                    .id(c.getId())
                    .title(c.getTitle())
                    .band(c.getBand())
                    .createdAt(c.getCreatedAt())
                    .updatedAt(c.getUpdatedAt())
                    .build());
        }
        return Optional.of(result);
    }

    @Transactional
    public Optional<Composition> createComposition(Composition req) {
        Composition composition = Composition.builder()
                .band(req.getBand())
                .title(req.getTitle())
                .build();
        Notesheet notesheet = Notesheet.builder()
                .instrument(instrumentService.getInstrumentById(1).orElseThrow())
                .composition(composition)
                .tuning(tuningService.getTuningById(1L).orElseThrow())
                .build();
        TimeSignature timeSignature = timeSignatureService.getTimeSignatureById(3L).orElseThrow();
        Bar bar = Bar.builder()
                .notesheet(notesheet)
                .orderIndex(1)
                .tempInBpm(120)
                .timeSignature(timeSignature)
                .build();
        notesheet.setBars(List.of(bar));
        composition.setNotesheets(List.of(notesheet));
        return Optional.of(compositionRepository.saveAndFlush(composition));
    }

    /**
     * Save / update composition from a CompositionSaveRequest (DTO, not JPA entity).
     *
     * Fix for the save-persistence bug (Hibernate 6 StaleObjectStateException):
     * The incoming JSON graph contains detached entities with existing IDs. After clearing
     * the notesheets (orphanRemoval deletes them from DB), Hibernate 6 rejects any attempt
     * to re-attach entities that reference those now-deleted rows.
     *
     * Solution: null out all IDs in the incoming graph so that Hibernate treats every
     * notesheet/bar/beat/beatnote as a fresh INSERT while keeping the correct parent references.
     */
    @Transactional
    public Optional<Composition> saveComposition(CompositionSaveRequest req) {
        Composition composition = compositionRepository.findById(req.getId())
                .orElseThrow(() -> new EmptyResultDataAccessException("Composition not found: " + req.getId(), 1));
        composition.getNotesheets().clear();
        compositionRepository.flush();

        if (req.getNotesheets() != null) {
            for (Notesheet ns : req.getNotesheets()) {
                ns.setId(null);
                ns.setComposition(composition);
                if (ns.getBars() != null) {
                    for (Bar bar : ns.getBars()) {
                        bar.setId(null);
                        bar.setNotesheet(ns);
                        if (bar.getBeats() != null) {
                            for (Beat beat : bar.getBeats()) {
                                beat.setId(null);
                                beat.setBar(bar);
                                if (beat.getBeatNotes() != null) {
                                    for (BeatNote bn : beat.getBeatNotes()) {
                                        bn.setId(null);
                                        bn.setBeat(beat);
                                    }
                                }
                            }
                        }
                    }
                }
                composition.getNotesheets().add(ns);
            }
        }
        composition.setUpdatedAt(Date.valueOf(LocalDate.now()));
        return Optional.of(compositionRepository.saveAndFlush(composition));
    }

    @Transactional
    public void deleteComposition(Long id) {
        Composition composition = compositionRepository.findById(id)
                .orElseThrow(() -> new EmptyResultDataAccessException("Composition not found: " + id, 1));
        compositionRepository.delete(composition);
    }
}

package ru.imit.service.composition.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.imit.service.composition.api.dto.AddBarRequest;
import ru.imit.service.composition.api.dto.InsertBeatRequest;
import ru.imit.service.models.*;
import ru.imit.service.repositories.NotesheetRepository;
import ru.imit.service.services.DurationService;
import ru.imit.service.services.TimeSignatureService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Point-edit operations on a notesheet (bar/beat) without full-graph save.
 */
@Service
public class NotesheetEditUseCase {

    private static final long DEFAULT_TIME_SIGNATURE_ID = 3L;
    private static final long DEFAULT_DURATION_ID = 4L; // QUARTER
    private static final int DEFAULT_TEMPO = 120;

    @Autowired
    private NotesheetRepository notesheetRepository;
    @Autowired
    private TimeSignatureService timeSignatureService;
    @Autowired
    private DurationService durationService;

    @Transactional
    public Notesheet addBar(Long compositionId, Long notesheetId, AddBarRequest req) {
        Notesheet notesheet = requireNotesheet(compositionId, notesheetId);
        ensureBarsList(notesheet);

        int at = req.getAtOrderIndex() != null ? req.getAtOrderIndex() : 1;
        String side = req.getSide() != null ? req.getSide().trim().toLowerCase() : "right";
        int insertAt;
        if ("left".equals(side)) {
            insertAt = at;
            for (Bar bar : notesheet.getBars()) {
                if (bar.getOrderIndex() != null && bar.getOrderIndex() >= insertAt) {
                    bar.setOrderIndex(bar.getOrderIndex() + 1);
                }
            }
        } else {
            insertAt = at + 1;
            for (Bar bar : notesheet.getBars()) {
                if (bar.getOrderIndex() != null && bar.getOrderIndex() > at) {
                    bar.setOrderIndex(bar.getOrderIndex() + 1);
                }
            }
        }

        int tempo = req.getTempInBpm() != null ? req.getTempInBpm() : DEFAULT_TEMPO;
        TimeSignature ts = timeSignatureService.getTimeSignatureById(DEFAULT_TIME_SIGNATURE_ID).orElseThrow();
        Duration duration = durationService.getDurationById(DEFAULT_DURATION_ID).orElseThrow();

        Bar newBar = Bar.builder()
                .notesheet(notesheet)
                .orderIndex(insertAt)
                .tempInBpm(tempo)
                .timeSignature(ts)
                .beats(new ArrayList<>())
                .build();
        Beat defaultBeat = Beat.builder()
                .bar(newBar)
                .orderIndex(1)
                .duration(duration)
                .beatNotes(new ArrayList<>())
                .build();
        newBar.getBeats().add(defaultBeat);
        notesheet.getBars().add(newBar);

        return notesheetRepository.saveAndFlush(notesheet);
    }

    @Transactional
    public Notesheet deleteBar(Long compositionId, Long notesheetId, Integer orderIndex) {
        Notesheet notesheet = requireNotesheet(compositionId, notesheetId);
        ensureBarsList(notesheet);

        boolean removed = notesheet.getBars().removeIf(
                bar -> Objects.equals(bar.getOrderIndex(), orderIndex));
        if (!removed) {
            throw new EmptyResultDataAccessException(
                    "Bar not found at orderIndex " + orderIndex, 1);
        }
        notesheet.getBars().sort(Comparator.comparing(
                b -> b.getOrderIndex() != null ? b.getOrderIndex() : 0));
        int i = 1;
        for (Bar bar : notesheet.getBars()) {
            bar.setOrderIndex(i++);
        }
        return notesheetRepository.saveAndFlush(notesheet);
    }

    @Transactional
    public Notesheet upsertBeat(
            Long compositionId,
            Long notesheetId,
            Integer barOrder,
            Integer beatOrder,
            Beat payload) {
        Notesheet notesheet = requireNotesheet(compositionId, notesheetId);
        Bar bar = requireBar(notesheet, barOrder);
        ensureBeatsList(bar);

        Beat beat = bar.getBeats().stream()
                .filter(b -> Objects.equals(b.getOrderIndex(), beatOrder))
                .findFirst()
                .orElse(null);

        if (beat == null) {
            beat = Beat.builder()
                    .bar(bar)
                    .orderIndex(beatOrder)
                    .beatNotes(new ArrayList<>())
                    .build();
            bar.getBeats().add(beat);
        }

        if (payload != null && payload.getDuration() != null && payload.getDuration().getId() != null) {
            Duration duration = durationService.getDurationById(payload.getDuration().getId()).orElseThrow();
            beat.setDuration(duration);
        } else if (beat.getDuration() == null) {
            beat.setDuration(durationService.getDurationById(DEFAULT_DURATION_ID).orElseThrow());
        }

        if (beat.getBeatNotes() == null) {
            beat.setBeatNotes(new ArrayList<>());
        } else {
            beat.getBeatNotes().clear();
        }
        if (payload != null && payload.getBeatNotes() != null) {
            for (BeatNote bn : payload.getBeatNotes()) {
                BeatNote copy = BeatNote.builder()
                        .beat(beat)
                        .noteOctave(bn.getNoteOctave())
                        .position(bn.getPosition())
                        .build();
                beat.getBeatNotes().add(copy);
            }
        }

        return notesheetRepository.saveAndFlush(notesheet);
    }

    @Transactional
    public Notesheet insertBeat(
            Long compositionId,
            Long notesheetId,
            Integer barOrder,
            InsertBeatRequest req) {
        Notesheet notesheet = requireNotesheet(compositionId, notesheetId);
        Bar bar = requireBar(notesheet, barOrder);
        ensureBeatsList(bar);

        int after = req != null && req.getAfterBeatOrder() != null ? req.getAfterBeatOrder() : 0;
        int newOrder = after + 1;
        for (Beat beat : bar.getBeats()) {
            if (beat.getOrderIndex() != null && beat.getOrderIndex() >= newOrder) {
                beat.setOrderIndex(beat.getOrderIndex() + 1);
            }
        }

        Duration duration = durationService.getDurationById(DEFAULT_DURATION_ID).orElseThrow();
        Beat newBeat = Beat.builder()
                .bar(bar)
                .orderIndex(newOrder)
                .duration(duration)
                .beatNotes(new ArrayList<>())
                .build();
        bar.getBeats().add(newBeat);
        bar.getBeats().sort(Comparator.comparing(
                b -> b.getOrderIndex() != null ? b.getOrderIndex() : 0));

        return notesheetRepository.saveAndFlush(notesheet);
    }

    @Transactional
    public Notesheet deleteBeat(
            Long compositionId,
            Long notesheetId,
            Integer barOrder,
            Integer beatOrder) {
        Notesheet notesheet = requireNotesheet(compositionId, notesheetId);
        Bar bar = requireBar(notesheet, barOrder);
        ensureBeatsList(bar);

        boolean removed = bar.getBeats().removeIf(
                beat -> Objects.equals(beat.getOrderIndex(), beatOrder));
        if (!removed) {
            throw new EmptyResultDataAccessException(
                    "Beat not found at orderIndex " + beatOrder, 1);
        }
        bar.getBeats().sort(Comparator.comparing(
                b -> b.getOrderIndex() != null ? b.getOrderIndex() : 0));
        int i = 1;
        for (Beat beat : bar.getBeats()) {
            beat.setOrderIndex(i++);
        }
        return notesheetRepository.saveAndFlush(notesheet);
    }

    private Notesheet requireNotesheet(Long compositionId, Long notesheetId) {
        Notesheet notesheet = notesheetRepository.findById(notesheetId)
                .orElseThrow(() -> new EmptyResultDataAccessException(
                        "Notesheet not found: " + notesheetId, 1));
        if (notesheet.getComposition() == null
                || !Objects.equals(notesheet.getComposition().getId(), compositionId)) {
            throw new EmptyResultDataAccessException(
                    "Notesheet " + notesheetId + " does not belong to composition " + compositionId, 1);
        }
        return notesheet;
    }

    private Bar requireBar(Notesheet notesheet, Integer barOrder) {
        ensureBarsList(notesheet);
        return notesheet.getBars().stream()
                .filter(bar -> Objects.equals(bar.getOrderIndex(), barOrder))
                .findFirst()
                .orElseThrow(() -> new EmptyResultDataAccessException(
                        "Bar not found at orderIndex " + barOrder, 1));
    }

    private void ensureBarsList(Notesheet notesheet) {
        if (notesheet.getBars() == null) {
            notesheet.setBars(new ArrayList<>());
        }
    }

    private void ensureBeatsList(Bar bar) {
        if (bar.getBeats() == null) {
            bar.setBeats(new ArrayList<>());
        }
    }
}

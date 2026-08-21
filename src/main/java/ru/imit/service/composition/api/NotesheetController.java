package ru.imit.service.composition.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.imit.service.composition.api.dto.AddBarRequest;
import ru.imit.service.composition.api.dto.InsertBeatRequest;
import ru.imit.service.composition.application.NotesheetEditUseCase;
import ru.imit.service.composition.application.NotesheetUseCase;
import ru.imit.service.dto.NotesheetCreateDTO;
import ru.imit.service.dto.NotesheetDTO;
import ru.imit.service.models.Beat;
import ru.imit.service.models.Notesheet;

import java.util.List;

@RestController
public class NotesheetController {

    private static final Logger log = LoggerFactory.getLogger(NotesheetController.class);

    @Autowired
    private NotesheetUseCase notesheetUseCase;
    @Autowired
    private NotesheetEditUseCase notesheetEditUseCase;

    @GetMapping("/composition/{id}/notesheets")
    public ResponseEntity<List<NotesheetDTO>> getNotesheetList(@PathVariable Long id) {
        return notesheetUseCase.listNotesheetsByComposition(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/notesheet/{id}")
    public ResponseEntity<Notesheet> getNotesheetById(@PathVariable Long id) {
        return notesheetUseCase.getNotesheetById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/notesheet/update")
    public ResponseEntity<Notesheet> updateNotesheet(@RequestBody Notesheet notesheet) {
        return notesheetUseCase.updateNotesheet(notesheet)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping("/notesheet/delete")
    public ResponseEntity<Void> deleteNotesheet(@RequestBody Long id) {
        notesheetUseCase.deleteNotesheet(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/notesheet/create")
    public ResponseEntity<Notesheet> createNotesheet(@RequestBody NotesheetCreateDTO notesheetCreateDTO) {
        return notesheetUseCase.createNotesheet(notesheetCreateDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping("/composition/{id}/notesheet/{notesheetId}/bars")
    public ResponseEntity<Notesheet> addBar(
            @PathVariable Long id,
            @PathVariable Long notesheetId,
            @RequestBody AddBarRequest req) {
        try {
            return ResponseEntity.ok(notesheetEditUseCase.addBar(id, notesheetId, req));
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/composition/{id}/notesheet/{notesheetId}/bars/{orderIndex}")
    public ResponseEntity<Notesheet> deleteBar(
            @PathVariable Long id,
            @PathVariable Long notesheetId,
            @PathVariable Integer orderIndex) {
        try {
            return ResponseEntity.ok(notesheetEditUseCase.deleteBar(id, notesheetId, orderIndex));
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/composition/{id}/notesheet/{notesheetId}/bars/{barOrder}/beats/{beatOrder}")
    public ResponseEntity<Notesheet> upsertBeat(
            @PathVariable Long id,
            @PathVariable Long notesheetId,
            @PathVariable Integer barOrder,
            @PathVariable Integer beatOrder,
            @RequestBody Beat beat) {
        try {
            return ResponseEntity.ok(
                    notesheetEditUseCase.upsertBeat(id, notesheetId, barOrder, beatOrder, beat));
        } catch (EmptyResultDataAccessException e) {
            log.warn("upsertBeat not found composition={} notesheet={} bar={} beat={}",
                    id, notesheetId, barOrder, beatOrder);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("upsertBeat failed composition={} notesheet={} bar={} beat={}",
                    id, notesheetId, barOrder, beatOrder, e);
            throw e;
        }
    }

    @PostMapping("/composition/{id}/notesheet/{notesheetId}/bars/{barOrder}/beats")
    public ResponseEntity<Notesheet> insertBeat(
            @PathVariable Long id,
            @PathVariable Long notesheetId,
            @PathVariable Integer barOrder,
            @RequestBody InsertBeatRequest req) {
        try {
            return ResponseEntity.ok(
                    notesheetEditUseCase.insertBeat(id, notesheetId, barOrder, req));
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/composition/{id}/notesheet/{notesheetId}/bars/{barOrder}/beats/{beatOrder}")
    public ResponseEntity<Notesheet> deleteBeat(
            @PathVariable Long id,
            @PathVariable Long notesheetId,
            @PathVariable Integer barOrder,
            @PathVariable Integer beatOrder) {
        try {
            return ResponseEntity.ok(
                    notesheetEditUseCase.deleteBeat(id, notesheetId, barOrder, beatOrder));
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

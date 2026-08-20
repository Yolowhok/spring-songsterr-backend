package ru.imit.service.composition.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.imit.service.composition.application.NotesheetUseCase;
import ru.imit.service.dto.NotesheetCreateDTO;
import ru.imit.service.dto.NotesheetDTO;
import ru.imit.service.models.Bar;
import ru.imit.service.models.Beat;
import ru.imit.service.models.BeatNote;
import ru.imit.service.models.Notesheet;

import java.util.List;
import java.util.Optional;

@RestController
public class NotesheetController {

    @Autowired
    private NotesheetUseCase notesheetUseCase;

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
}

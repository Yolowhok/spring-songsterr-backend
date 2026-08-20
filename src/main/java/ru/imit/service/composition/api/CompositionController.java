package ru.imit.service.composition.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.imit.service.composition.api.dto.CompositionSaveRequest;
import ru.imit.service.composition.application.CompositionUseCase;
import ru.imit.service.dto.CompositionDTO;
import ru.imit.service.models.Composition;

import java.util.List;
import java.util.Optional;

@RestController
public class CompositionController {

    @Autowired
    private CompositionUseCase compositionUseCase;

    @GetMapping("/composition/{id}")
    public ResponseEntity<CompositionDTO> getCompositionSummary(@PathVariable Long id) {
        return compositionUseCase.getCompositionSummaryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/composition/{id}/full")
    public ResponseEntity<Composition> getFullComposition(@PathVariable Long id) {
        return compositionUseCase.getFullCompositionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/compositions")
    public ResponseEntity<List<CompositionDTO>> listCompositions() {
        return compositionUseCase.listCompositions()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/composition/create")
    public ResponseEntity<Composition> createComposition(@RequestBody Composition composition) {
        return compositionUseCase.createComposition(composition)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping("/composition/update")
    public ResponseEntity<Composition> updateComposition(@RequestBody CompositionSaveRequest req) {
        return compositionUseCase.saveComposition(req)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping("/composition/save")
    public ResponseEntity<Composition> saveComposition(@RequestBody CompositionSaveRequest req) {
        return compositionUseCase.saveComposition(req)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }

    @PostMapping("/composition/delete/{id}")
    public ResponseEntity<Void> deleteComposition(@PathVariable Long id) {
        try {
            compositionUseCase.deleteComposition(id);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

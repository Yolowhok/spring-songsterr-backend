package ru.imit.service.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.imit.service.dto.CompositionDTO;
import ru.imit.service.models.Composition;
import ru.imit.service.services.CompositionService;

import java.util.List;
import java.util.Optional;
/**
 * get full Composition by ID
 * get Composition by ID
 * READ
 * DELETE
 * */
@RestController
public class CompositionController {
    @Autowired
    private CompositionService compositionService;
    /**
     * Возвращает только Composition, не возвращает содержимое объектов, если они являются полем Composition
     * */
    @GetMapping("/composition/{id}")
    public ResponseEntity<CompositionDTO> getOnlyCompositionById(@PathVariable Long id) {
        Optional<CompositionDTO> optionalComposition = compositionService.getOnlyCompositionById(id);
        if (optionalComposition.isPresent()) {
            return ResponseEntity.ok(optionalComposition.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    /**
     * Возвращает Composition вместе со всем вложенными объектами*/
    @GetMapping("/composition/{id}/full")
    public ResponseEntity<Composition> getFullCompositionById(@PathVariable Long id) {
        Optional<Composition> optionalComposition = compositionService.getFullCompositionById(id);
        if (optionalComposition.isPresent()) {
            return ResponseEntity.ok(optionalComposition.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    /**
     * Возвращает список всех Composition без вложенных объектов
     * */
    @GetMapping("/compositions")
    public ResponseEntity<List<CompositionDTO>> getAllOnlyCompositions() {
        Optional<List<CompositionDTO>> compositions = compositionService.getAllOnlyComposition();
        if (compositions.isPresent()) {
            return ResponseEntity.ok(compositions.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    /**
     * Возвращает список всех Composition с вложенными объектами
     * */
    @GetMapping("/compositions/full")
    public ResponseEntity<List<Composition>> getAllFullCompositions() {
        Optional<List<Composition>> compositions = compositionService.getAllFullComposition();
        if (compositions.isPresent()) {
            return ResponseEntity.ok(compositions.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    /**
     * Создаёт новую запись Composition в таблице
     * */
    @PostMapping("/composition/create")
    public ResponseEntity<Composition> createComposition(@RequestBody Composition composition) {
        Optional<Composition> compositionOptional = compositionService.saveComposition(composition);
        if (compositionOptional.isPresent()) {
            return ResponseEntity.ok(compositionOptional.get());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    /**
     * Обновляет существующую запись Composition
     * */
    @PostMapping("/composition/update")
    public ResponseEntity<Composition> updateComposition(@RequestBody Composition composition) {
        System.out.println(composition);
        Optional<Composition> compositionOptional = compositionService.updateComposition(composition);
        if (compositionOptional.isPresent()) {
            return ResponseEntity.ok(compositionOptional.get());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/composition/save")
    public ResponseEntity<Composition> saveComposition(@RequestBody Composition composition) {
        System.out.println(composition);
        Optional<Composition> compositionOptional = compositionService.updateComposition(composition);
        if (compositionOptional.isPresent()) {
            return ResponseEntity.ok(compositionOptional.get());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}

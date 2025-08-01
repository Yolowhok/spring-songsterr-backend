package ru.imit.service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.imit.service.models.TimeSignature;
import ru.imit.service.models.Tuning;
import ru.imit.service.services.TimeSignatureService;
import ru.imit.service.services.TuningService;

import java.util.List;
import java.util.Optional;

@RestController
public class TuningController {
    @Autowired
    TuningService tuningService;

    @GetMapping("/static/tunings")
    public ResponseEntity<List<Tuning>> getTunings() {
        Optional<List<Tuning>> tuningList = tuningService.getTunings();
        if (tuningList.isPresent()) {
            return ResponseEntity.ok(tuningList.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }




}

package ru.imit.service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.imit.service.enums.DurationType;
import ru.imit.service.models.Duration;
import ru.imit.service.services.DurationService;

import java.util.List;
import java.util.Optional;

@RestController
public class DurationController {
    @Autowired
    DurationService durationService;
    @GetMapping("/static/durations")
    public ResponseEntity<List<Duration>> getDurations() {
        Optional<List<Duration>> optionalDurations = durationService.getDurations();
        if (optionalDurations.isPresent()) {
            return ResponseEntity.ok(optionalDurations.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/static/duration/name/{name}")
    public ResponseEntity<Duration> getDurationByName(@PathVariable String name) {
        try {
            Optional<Duration> optionalDuration = durationService.getDurationByName(DurationType.valueOf(name));
            if (optionalDuration.isPresent()) {
                return ResponseEntity.ok(optionalDuration.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/static/duration/{id}")
    public ResponseEntity<Duration> getDurationById(@PathVariable Long id) {
        Optional<Duration> optionalDuration = durationService.getDurationById(id);
        if (optionalDuration.isPresent()) {
            return ResponseEntity.ok(optionalDuration.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

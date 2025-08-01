package ru.imit.service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.imit.service.models.TimeSignature;
import ru.imit.service.services.TimeSignatureService;

import java.util.List;
import java.util.Optional;

@RestController
public class TimeSignatureController {
    @Autowired
    TimeSignatureService timeSignatureService;

    @GetMapping("/static/timeSignatures")
    public ResponseEntity<List<TimeSignature>> getTimeSignatures() {
        Optional<List<TimeSignature>> timeSignatureList = timeSignatureService.getTimeSignatures();
        if (timeSignatureList.isPresent()) {
            return ResponseEntity.ok(timeSignatureList.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/static/timeSignature/{id}")
    public ResponseEntity<TimeSignature> getTimeSignatureById(@PathVariable Long id) {
        Optional<TimeSignature> timeSignature = timeSignatureService.getTimeSignatureById(id);
        if (timeSignature.isPresent()) {
            return ResponseEntity.ok(timeSignature.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/hidden/static/timeSignature")
    public ResponseEntity<TimeSignature> updateTimeSignature(@RequestBody TimeSignature timeSignature) {
        Optional<TimeSignature> timeSignatureOptional = timeSignatureService.updateTimeSignature(timeSignature);
        if (timeSignatureOptional.isPresent()) {
            return ResponseEntity.ok(timeSignatureOptional.get());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/static/timeSignature")
    public ResponseEntity<TimeSignature> saveTimeSignature(@RequestBody TimeSignature timeSignature) {
        Optional<TimeSignature> timeSignatureOptional = timeSignatureService.saveTimeSignature(timeSignature);
        if (timeSignatureOptional.isPresent()) {
            return ResponseEntity.ok(timeSignatureOptional.get());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}

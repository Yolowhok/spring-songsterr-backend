package ru.imit.service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.imit.service.enums.OctaveType;
import ru.imit.service.models.Octave;
import ru.imit.service.services.OctaveService;

import java.util.List;
import java.util.Optional;

@RestController
public class OctaveController {
    @Autowired
    OctaveService octaveService;
    @GetMapping("/static/octaves")
    public ResponseEntity<List<Octave>> getOctaves() {
        Optional<List<Octave>> octaveList = octaveService.getOctaves();
        if (octaveList.isPresent()) {
            return ResponseEntity.ok(octaveList.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/static/octave/{id}")
    public ResponseEntity<Octave> getOctaves(@PathVariable Long id) {
        Optional<Octave> octave = octaveService.getOctaveById(id);
        if (octave.isPresent()) {
            return ResponseEntity.ok(octave.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/static/octave/name/{name}")
    public ResponseEntity<Octave> getOctaves(@PathVariable String name) {
        try {
            Optional<Octave> octave = octaveService.getOctaveByName(OctaveType.valueOf(name));
            if (octave.isPresent()) {
                return ResponseEntity.ok(octave.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

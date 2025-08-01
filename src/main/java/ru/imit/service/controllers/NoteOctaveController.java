package ru.imit.service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.imit.service.models.NoteOctave;
import ru.imit.service.services.NoteOctaveService;

import java.util.List;
import java.util.Optional;

@RestController
public class NoteOctaveController {
    @Autowired
    NoteOctaveService noteOctaveService;

    @GetMapping("/static/noteoctaves")
    public ResponseEntity<List<NoteOctave>> getNoteOctaves() {
        Optional<List<NoteOctave>> noteOctaveList = noteOctaveService.getNoteOctaves();
        if (noteOctaveList.isPresent()) {
            return ResponseEntity.ok(noteOctaveList.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/static/noteoctaves/ordered")
    public ResponseEntity<List<NoteOctave>> getNoteOctavesOrdered() {
        Optional<List<NoteOctave>> noteOctaveList = noteOctaveService.getNoteOctavesOrdered();
        if (noteOctaveList.isPresent()) {
            return ResponseEntity.ok(noteOctaveList.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

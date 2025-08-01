package ru.imit.service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.imit.service.enums.NoteType;
import ru.imit.service.models.Note;
import ru.imit.service.services.NoteService;

import java.util.List;
import java.util.Optional;

@RestController
public class NoteController {
    @Autowired
    NoteService noteService;
    @GetMapping("/static/notes")
    public ResponseEntity<List<Note>> getNotes() {
        Optional<List<Note>> noteOptional = noteService.getNotes();
        if (noteOptional.isPresent()) {
            return ResponseEntity.ok(noteOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/static/note/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable Long id) {
        Optional<Note> noteOptional = noteService.getNoteById(id);
        if (noteOptional.isPresent()) {
            return ResponseEntity.ok(noteOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/static/note/name/{name}")
    public ResponseEntity<Note> getNoteByName(@PathVariable String name) {
        try {
            Optional<Note> noteOptional = noteService.getNoteByName(NoteType.valueOf(name));
            if (noteOptional.isPresent()) {
                return ResponseEntity.ok(noteOptional.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

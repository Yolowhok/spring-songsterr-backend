package ru.imit.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.imit.service.enums.NoteType;
import ru.imit.service.models.Note;
import ru.imit.service.repositories.NoteRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class NoteService {
    @Autowired
    private NoteRepository noteRepository;

    public Optional<Note> getNoteById(Long id) {
        try {
            return Optional.ofNullable(noteRepository.getOne(id));
        } catch (EntityNotFoundException e) {
            return Optional.empty();
        }
    }
    public Optional<List<Note>> getNotes() {
        try {
            return Optional.ofNullable(noteRepository.findAll());
        } catch (EntityNotFoundException e) {
            return Optional.empty();
        }
    }
    public Optional<Note> getNoteByName(NoteType type) {
        try {
            return Optional.ofNullable(noteRepository.getNoteByName(type.toString()));
        } catch (EntityNotFoundException e) {
            return Optional.empty();
        }
    }
}

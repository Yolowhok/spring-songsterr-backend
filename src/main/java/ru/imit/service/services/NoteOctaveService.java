package ru.imit.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.imit.service.models.NoteOctave;
import ru.imit.service.repositories.NoteOctaveRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class NoteOctaveService {
    @Autowired
    NoteOctaveRepository noteOctaveRepository;

    public Optional<List<NoteOctave>> getNoteOctaves() {
        try {
            return Optional.ofNullable(noteOctaveRepository.findAll());
        } catch (EntityNotFoundException e) {
            return Optional.empty();
        }
    }
    public Optional<List<NoteOctave>> getNoteOctavesOrdered() {
        try {
            return Optional.ofNullable(noteOctaveRepository.getNoteOctavesOrdered());
        } catch (EntityNotFoundException e) {
            return Optional.empty();
        }
    }
}

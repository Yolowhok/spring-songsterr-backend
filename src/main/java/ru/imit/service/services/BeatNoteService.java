package ru.imit.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.imit.service.models.BeatNote;
import ru.imit.service.repositories.BeatNoteRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class BeatNoteService {
    @Autowired
    private BeatNoteRepository beatNoteRepository;

    public Optional<BeatNote> getBeatNoteById(Long id) {
        try {
            return Optional.ofNullable(beatNoteRepository.getOne(id));
        } catch(EntityNotFoundException e) {
            return Optional.empty();
        }
    }
}

package ru.imit.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.imit.service.models.Beat;
import ru.imit.service.repositories.BeatRepository;

import javax.persistence.EntityNotFoundException;
import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class BeatService {
    @Autowired
    private BeatRepository beatRepository;

    public Optional<Beat> getBeatById(Long id) {
        try {
            return Optional.ofNullable(beatRepository.getOne(id));
        } catch (EntityNotFoundException e) {
            return Optional.empty();
        }
    }
}

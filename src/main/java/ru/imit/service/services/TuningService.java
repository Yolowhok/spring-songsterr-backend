package ru.imit.service.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import ru.imit.service.models.Tuning;
import ru.imit.service.repositories.TuningRepository;

import jakarta.persistence.EntityExistsException;
import java.util.List;
import java.util.Optional;

@Service
public class TuningService {
    @Autowired
    private TuningRepository tuningRepository;

    public Optional<Tuning> getTuningById(Long id) {
        try {
            return Optional.ofNullable(tuningRepository.getReferenceById(id));
        } catch (EntityExistsException e) {
            return Optional.empty();
        }
    }
    public Optional<Tuning> getTuningByName(String name) {
        try {
            return tuningRepository.findOne(Example.of(Tuning.builder().name(name).build()));
        } catch (EntityExistsException e) {
            return Optional.empty();
        }
    }
    public Optional<List<Tuning>> getTunings() {
        try {
            return Optional.ofNullable(tuningRepository.findAll());
        } catch (EntityExistsException e) {
            return Optional.empty();
        }
    }
}

package ru.imit.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.imit.service.enums.DurationType;
import ru.imit.service.models.Duration;
import ru.imit.service.repositories.DurationRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class DurationService {
    @Autowired
    private DurationRepository durationRepository;
    public Optional<List<Duration>> getDurations() {
        try {
            return Optional.ofNullable(durationRepository.findAll());
        } catch (EntityNotFoundException e) {
            return Optional.empty();
        }
    }
    public Optional<Duration> getDurationById(Long id) {
        try {
            return Optional.ofNullable(durationRepository.getOne(id));
        } catch (EntityNotFoundException e) {
            return Optional.empty();
        }
    }
    public Optional<Duration> getDurationByName(DurationType type) {
        try {
            return Optional.ofNullable(durationRepository.getDurationByName(type.toString()));
        } catch (EntityNotFoundException e) {
            return Optional.empty();
        }
    }
}

package ru.imit.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.imit.service.models.TimeSignature;
import ru.imit.service.repositories.TimeSignatureRepository;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

@Service
public class TimeSignatureService {

    @Autowired
    private TimeSignatureRepository timeSignatureRepository;

    public Optional<TimeSignature> getTimeSignatureById(Long id) {
        try {
            return Optional.ofNullable(timeSignatureRepository.getOne(id));
        } catch (EntityExistsException e) {
            return Optional.empty();
        }
    }
    public Optional<List<TimeSignature>> getTimeSignatures() {
        try {
            return Optional.ofNullable(timeSignatureRepository.findAll());
        } catch (EntityNotFoundException e) {
            return Optional.empty();
        }
    }

    public Optional<TimeSignature> updateTimeSignature(TimeSignature timeSignature) {
        try {
            return Optional.ofNullable(timeSignatureRepository.saveAndFlush(timeSignature));
        } catch (ConstraintViolationException e) {
            return Optional.empty();
        }
    }

    public Optional<TimeSignature> saveTimeSignature(TimeSignature timeSignature) {
        try {
            timeSignature.setId(null);
            return Optional.ofNullable(timeSignatureRepository.saveAndFlush(timeSignature));
        } catch (DataIntegrityViolationException e) {
            return Optional.empty();
        }
    }
}

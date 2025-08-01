package ru.imit.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.imit.service.enums.OctaveType;
import ru.imit.service.models.Octave;
import ru.imit.service.repositories.OctaveRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class OctaveService {
    @Autowired
    private OctaveRepository octaveRepository;

    public Optional<Octave> getOctaveById(Long id) {
        try {
            return Optional.ofNullable(octaveRepository.getOne(id));
        } catch (EntityNotFoundException e) {
            return Optional.empty();
        }
    }
    public Optional<List<Octave>> getOctaves() {
        try {
            return Optional.ofNullable(octaveRepository.findAll());
        } catch (EntityNotFoundException e) {
            return Optional.empty();
        }
    }
    public Optional<Octave> getOctaveByName(OctaveType type) {
        try {
            return Optional.ofNullable(octaveRepository.getOctaveByName(type.toString()));
        } catch (EntityNotFoundException e) {
            return Optional.empty();
        }
    }
}

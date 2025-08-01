package ru.imit.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.imit.service.enums.InstrumentType;
import ru.imit.service.models.Instrument;
import ru.imit.service.repositories.InstrumentRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class InstrumentService {

    @Autowired
    private InstrumentRepository instrumentRepository;
    public Optional<Instrument> getInstrumentByName(InstrumentType name) {
        try {
            return Optional.ofNullable(instrumentRepository.findByName(name.toString()));
        } catch (EntityNotFoundException e) {
            return Optional.empty();
        }
    }
    public Optional<List<Instrument>> getInstruments() {
        try {
            return Optional.ofNullable(instrumentRepository.findAll());
        } catch (EntityNotFoundException e) {
            return Optional.empty();
        }
    }
    public Optional<Instrument> getInstrumentById(Integer id) {
        try {
            return Optional.ofNullable(instrumentRepository.getOne(id));
        } catch (EntityNotFoundException e) {
            return Optional.empty();
        }
    }
}
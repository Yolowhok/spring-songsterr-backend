package ru.imit.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.imit.service.models.Bar;
import ru.imit.service.repositories.BarRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class BarService {
    @Autowired
    private BarRepository barRepository;

    Optional<Bar> getBarById(Long id) {
        try {
            return Optional.ofNullable(barRepository.getOne(id));
        } catch (EntityNotFoundException e) {
            return Optional.empty();
        }
    }


}

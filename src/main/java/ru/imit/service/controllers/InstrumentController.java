package ru.imit.service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.imit.service.enums.InstrumentType;
import ru.imit.service.models.Instrument;
import ru.imit.service.services.InstrumentService;

import java.util.List;
import java.util.Optional;

@RestController
public class InstrumentController {

    @Autowired
    private InstrumentService instrumentService;
    @GetMapping("/static/instruments")
    public ResponseEntity<List<Instrument>> getInstruments() {
        Optional<List<Instrument>> optionalInstrument = instrumentService.getInstruments();
        if (optionalInstrument.isPresent()) {
            return ResponseEntity.ok(optionalInstrument.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/static/instrument/{id}")
    public ResponseEntity<Instrument> getInstrumentById(@PathVariable Integer id) {
        Optional<Instrument> optionalInstrument = instrumentService.getInstrumentById(id);
        if (optionalInstrument.isPresent()) {
            return ResponseEntity.ok(optionalInstrument.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


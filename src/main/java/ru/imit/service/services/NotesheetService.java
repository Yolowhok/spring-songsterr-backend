package ru.imit.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.annotation.Transactional;
import ru.imit.service.dto.NotesheetCreateDTO;
import ru.imit.service.dto.NotesheetDTO;
import ru.imit.service.models.*;
import ru.imit.service.repositories.CompositionRepository;
import ru.imit.service.repositories.NotesheetRepository;

import jakarta.persistence.EntityNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NotesheetService {
    @Autowired
    private NotesheetRepository notesheetRepository;
    @Autowired
    private CompositionRepository compositionRepository;
    @Autowired TimeSignatureService timeSignatureService;
    public Optional<Notesheet> getNotesheetById(Long id) {
        try {
            return notesheetRepository.findById(id);
        } catch (EntityNotFoundException e) {
            return Optional.empty();
        }
    }
    public Optional<Notesheet> updateNotesheet(Notesheet notesheet) {
        notesheet.setComposition(notesheetRepository.getReferenceById(notesheet.getId()).getComposition());
        notesheetRepository.save(notesheet);
        return Optional.ofNullable(notesheetRepository.saveAndFlush(notesheet));
    }
    public Optional<Notesheet> saveNotesheet(Notesheet notesheet) {
        return Optional.ofNullable(notesheetRepository.saveAndFlush(notesheet));
    }
    public Optional<List<Notesheet>> getAllNotesheetsByCompositionId(Long id) {
        return Optional.ofNullable(notesheetRepository.getNotesheetsById(id));
    }

    public Optional<List<NotesheetDTO>> getAllNotesheetsDTOByCompositionId(Long id) {
        Optional<List<Notesheet>> optionalNotesheetList = getAllNotesheetsByCompositionId(id);

        if (optionalNotesheetList.isPresent()) {
            List<NotesheetDTO> notesheetDTOList = new ArrayList<>();
            for (Notesheet notesheet :
                    optionalNotesheetList.get()) {
                notesheetDTOList.add(NotesheetDTO.builder()
                        .id(notesheet.getId())
                        .tuning(notesheet.getTuning())
                        .instrument(notesheet.getInstrument()).build());
            }
            return Optional.ofNullable(notesheetDTOList);
        } else {
            return Optional.empty();
        }

    }
    public void deleteNotesheet(Long id) {
        if (!notesheetRepository.existsById(id)) {
            throw new EntityNotFoundException("Notesheet with id " + id + " not found");
        }
        notesheetRepository.deleteById(id);
    }

    @Transactional
    public Optional<Notesheet> createNotesheet(NotesheetCreateDTO notesheetCreateDTO) {
        try {
            Tuning tuning = notesheetCreateDTO.getTuning();
            Instrument instrument = notesheetCreateDTO.getInstrument();
            Long compositionID = notesheetCreateDTO.getCompositionID();
            Composition composition = compositionRepository.getReferenceById(compositionID);
            Notesheet notesheet = Notesheet.builder()
                    .tuning(tuning)
                    .instrument(instrument)
                    .composition(composition)
                    .build();
            TimeSignature timeSignature = timeSignatureService.getTimeSignatureById(3L).get();

            Bar bar = Bar.builder()
                    .notesheet(notesheet)
                    .orderIndex(1)
                    .tempInBpm(120)
                    .timeSignature(timeSignature).build();
            notesheet.setBars(List.of(bar));
            Notesheet newNotesheet = notesheetRepository.save(notesheet);
            return Optional.of(newNotesheet);

        } catch (TransactionException e) {
            return Optional.empty();
        }
    }
}

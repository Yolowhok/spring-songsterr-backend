package ru.imit.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.imit.service.dto.NotesheetDTO;
import ru.imit.service.models.Notesheet;
import ru.imit.service.repositories.CompositionRepository;
import ru.imit.service.repositories.NotesheetRepository;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NotesheetService {
    @Autowired
    private NotesheetRepository notesheetRepository;
    @Autowired
    private CompositionRepository compositionRepository;
    public Optional<Notesheet> getNotesheetById(Long id) {
        try {
            return Optional.ofNullable(notesheetRepository.findOne(id));
        } catch (EntityNotFoundException e) {
            return Optional.empty();
        }
    }
    public Optional<Notesheet> updateNotesheet(Notesheet notesheet) {
        notesheet.setComposition(notesheetRepository.getOne(notesheet.getId()).getComposition());
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
        if (notesheetRepository.exists(id)) {
            throw new EntityNotFoundException("Notesheet with id " + id + " not found");
        }
        notesheetRepository.delete(id);
    }
}

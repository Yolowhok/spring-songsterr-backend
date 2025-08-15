package ru.imit.service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.imit.service.dto.CompositionDTO;
import ru.imit.service.models.*;
import ru.imit.service.repositories.CompositionRepository;
import ru.imit.service.repositories.NotesheetRepository;

import javax.persistence.EntityNotFoundException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 1. Get full composition by ID
 * 2. Get only composition by ID | Composition DTO
 * 3. Get all only compositions | Composition DTO
 * 4. Get all full compositions
 * 5. Update composition
 * 6. Save (create) composition
 * */
@Service
public class CompositionService {
    @Autowired
    private CompositionRepository compositionRepository;
    @Autowired
    private InstrumentService instrumentService;
    @Autowired
    private TuningService tuningService;
    @Autowired
    private TimeSignatureService timeSignatureService;
    public Optional<CompositionDTO> getOnlyCompositionById(Long id) {
        Optional<Composition> compositionOptional;
        try {
            compositionOptional= Optional.ofNullable(compositionRepository.getOnlyCompositionById(id));
        } catch (EntityNotFoundException e) {
            compositionOptional = Optional.empty();
        }
        if (compositionOptional.isPresent()) {
            Composition composition = compositionOptional.get();
            CompositionDTO compositionDTO = CompositionDTO
                    .builder()
                    .id(composition.getId())
                    .title(composition.getTitle())
                    .band(composition.getBand())
                    .createdAt(composition.getCreatedAt())
                    .updatedAt(composition.getUpdatedAt()).build();
            return Optional.ofNullable(compositionDTO);
        } else {
            return Optional.empty();
        }
    }
    public Optional<Composition> getFullCompositionById(Long id) {
        try {
            return Optional.ofNullable(compositionRepository.getOne(id));
        } catch (EntityNotFoundException e) {
            return Optional.empty();
        }
    }

    public Optional<List<CompositionDTO>> getAllOnlyComposition() {
        Optional<List<Composition>> optionalCompositions = getAllFullComposition();

        if (getAllFullComposition().isPresent()) {
            List<Composition> compositions = optionalCompositions.get();
            List<CompositionDTO> compositionDTOS = new ArrayList<>();
            for (Composition composition :
                    compositions) {
                compositionDTOS.add(CompositionDTO
                        .builder()
                        .id(composition.getId())
                        .title(composition.getTitle())
                        .band(composition.getBand())
                        .createdAt(composition.getCreatedAt())
                        .updatedAt(composition.getUpdatedAt())
                        .build());
            }
            return Optional.ofNullable(compositionDTOS);
        } else {
            return Optional.empty();
        }

    }
    public Optional<List<Composition>> getAllFullComposition() {
        List<Composition> compositions = compositionRepository.findAll();
        if (!compositions.isEmpty()) {
            return Optional.ofNullable(compositions);
        } else {
            return Optional.empty();
        }
    }
    public Optional<Composition> updateComposition(Composition compositionUpdate) {
        Composition composition = compositionRepository.getOne(compositionUpdate.getId());

//        if (compositionUpdate.getNotesheets() == null) {
//            compositionUpdate.setNotesheets(new ArrayList<>());
//        }

        composition.getNotesheets().clear();

//        for (Notesheet notesheet : compositionUpdate.getNotesheets()) {
//            System.out.println(notesheet);
//            notesheet.setComposition(composition);  // Важно установить родителя
//            composition.getNotesheets().add(notesheet);
//        }
        for (Notesheet notesheetDto : compositionUpdate.getNotesheets()) {
//            Notesheet fullNotesheet = notesheetRepository.findOne(notesheetDto.getId());
            notesheetDto.setComposition(composition);
            composition.getNotesheets().add(notesheetDto);
        }

        compositionRepository.saveAndFlush(composition);

        return Optional.ofNullable(composition);
    }
    public Optional<Composition> saveComposition(Composition composition) {
        composition.setId(null);
        return Optional.ofNullable(compositionRepository.saveAndFlush(composition));
    }
    @Transactional
    public Optional<Composition> createNewComposition(Composition composition) {

        Composition compositionDTO = Composition.builder()
                .band(composition.getBand())
                .title(composition.getTitle())
                .build();
        Notesheet notesheet = Notesheet.builder()
                .instrument(instrumentService.getInstrumentById(1).get())
                .composition(compositionDTO)
                .tuning(tuningService.getTuningById(1L).get())
                .build();
        TimeSignature timeSignature = timeSignatureService.getTimeSignatureById(3L).get();
        Bar bar = Bar.builder()
                .notesheet(notesheet)
                .orderIndex(1)
                .tempInBpm(120)
                .timeSignature(timeSignature).build();
        notesheet.setBars(List.of(bar));

        compositionDTO.setNotesheets(List.of(notesheet));
        return Optional.ofNullable(compositionRepository.saveAndFlush(compositionDTO));
    }
    @Transactional
    public void deleteComposition(Long id) {
        // Каскадное удаление должно быть настроено в Entity
        Composition composition = Optional.of(compositionRepository.findOne(id))
                .orElseThrow(() -> new EmptyResultDataAccessException("Composition not found with id: " + id, 1));

        compositionRepository.delete(composition);
    }
}

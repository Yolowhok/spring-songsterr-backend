package ru.imit.service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.imit.service.dto.NotesheetDTO;
import ru.imit.service.models.Bar;
import ru.imit.service.models.Beat;
import ru.imit.service.models.BeatNote;
import ru.imit.service.models.Notesheet;
import ru.imit.service.services.NotesheetService;

import java.util.List;
import java.util.Optional;

@RestController
public class NotesheetController {

    /**
     * Автоматическая инъекция зависимости
     * */
    @Autowired
    private NotesheetService notesheetService;


    @GetMapping("/composition/{id}/notesheets")
    public ResponseEntity<List<NotesheetDTO>> getNotesheetList(@PathVariable Long id) {
        Optional<List<NotesheetDTO>> optionalNotesheetList = notesheetService.getAllNotesheetsDTOByCompositionId(id);
        if (optionalNotesheetList.isPresent()) {
            return ResponseEntity.ok(optionalNotesheetList.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Метод для получения Notesheet по id
     *  */
    @GetMapping("/notesheet/{id}")
    public ResponseEntity<Notesheet> getNotesheetById(@PathVariable Long id) {
        Optional<Notesheet> optionalNotesheet = notesheetService.getNotesheetById(id);
        if (optionalNotesheet.isPresent()) {
            System.out.println(optionalNotesheet.get());
            return ResponseEntity.ok(optionalNotesheet.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Метод перезаписывает Notesheet
     * -
     * Из-за природы данных, сложность обновления Notesheet составляет O(n^3)
     * Поскольку ArrayList предоставляет доступ к элементам по индексу за константное время O(1),
     * проход по всем элементам выполняется эффективно.
     * Однако основное время выполнения операции будет определяться количеством элементов в ArrayList.
     * -
     * Какие бы изменения в Notesheet не сделаны, перезаписываются записи даже те, которые не были обновлены.
     * Из-за предположения, что накладные расходы на проверку изменённых записей и неизмененных записей будут
     * компенсировать потенциальный выигрыш от варианта, при котором перезаписываются только фактически
     * изменённые записи
     * -
     * */
    @PostMapping("/notesheet/update")
    public ResponseEntity<Notesheet> postNotesheetSave(@RequestBody Notesheet notesheet) {
        //Устанавливаем обратную связь для Bar, Beat и BeatNotes
        for (Bar bar : notesheet.getBars()) {
            bar.setNotesheet(notesheet);
            for (Beat beat : bar.getBeats()) {
                beat.setBar(bar);
                for (BeatNote beatNote: beat.getBeatNotes()) {
                    beatNote.setBeat(beat);
                }
            }
        }
        System.out.println("CHECK NOTESHEET \n" + notesheet);
        notesheetService.updateNotesheet(notesheet);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/notesheet/delete")
    public ResponseEntity<Notesheet> postNotesheetDelete(@RequestBody Long id) {
        //Устанавливаем обратную связь для Bar, Beat и BeatNotes

        notesheetService.deleteNotesheet(id);
        return ResponseEntity.ok().build();
    }


}

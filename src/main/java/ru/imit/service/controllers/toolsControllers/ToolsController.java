package ru.imit.service.controllers.toolsControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.imit.service.Tools.CreateLinesSQL;
import ru.imit.service.models.NoteOctave;
import ru.imit.service.models.Notesheet;

import java.util.List;
import java.util.Optional;

@RestController
@Profile("tools")
public class ToolsController {
    @Autowired
    CreateLinesSQL createLinesSQL;

    @PostMapping("/tools/updateNoteOctave")
    public ResponseEntity<List<NoteOctave>> update() {
        Optional<List<NoteOctave>> noteOctaveList = createLinesSQL.update();
        if (noteOctaveList.isPresent()) {
            return ResponseEntity.ok(noteOctaveList.get());
        } else {
            return ResponseEntity.notFound().build();
        }

    }
    @GetMapping("/tools/create")
    public ResponseEntity<Notesheet> create() {
        Notesheet notesheet = createLinesSQL.create();
        return ResponseEntity.ok(notesheet);
    }
}

package ru.imit.service.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import ru.imit.service.enums.NoteType;

import javax.persistence.*;

@Entity
@Table(name = "note_octave")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class NoteOctave {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "note_id")
    Note note;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "octave_id")
    Octave octave;
}

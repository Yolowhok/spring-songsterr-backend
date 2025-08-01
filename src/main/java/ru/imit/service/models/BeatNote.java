package ru.imit.service.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "beat_note")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "beat")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class BeatNote {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beat_id")
    @JsonIgnore
    Beat beat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "note_octave_id")
    NoteOctave noteOctave;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    Position position;
//    @OneToMany(mappedBy = "beat", cascade = CascadeType.REMOVE, orphanRemoval = true)
//    List<NoteOctave> noteOctave;



}

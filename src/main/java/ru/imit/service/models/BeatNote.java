package ru.imit.service.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import jakarta.persistence.*;

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

    @Builder.Default
    @Column(name = "tied", nullable = false)
    Boolean tied = false;

    /** hammer | pull | slide_up | slide_down | bend */
    @Column(name = "technique", length = 32)
    String technique;

    /** half | full — only for bend */
    @Column(name = "bend_value", length = 16)
    String bendValue;
}

package ru.imit.service.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tuning_string")
@Entity
@ToString(exclude = "tuning")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class TuningString {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @Column
    private int stringNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tuning_id")
    @JsonIgnore
    private Tuning tuning;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "note_octave_id")
    private NoteOctave noteOctave;


}
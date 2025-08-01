package ru.imit.service.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import ru.imit.service.enums.NoteType;

import javax.persistence.*;

@Entity
@Table(name = "note")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class Note {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    NoteType name;

}

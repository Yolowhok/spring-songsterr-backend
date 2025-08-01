package ru.imit.service.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import ru.imit.service.enums.OctaveType;

import javax.persistence.*;

@Entity
@Table(name = "position")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class Position {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;

    @Column(name = "string")
    Integer string;

    @Column(name = "fret")
    Integer fret;

}

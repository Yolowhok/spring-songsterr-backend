package ru.imit.service.models;


import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import ru.imit.service.enums.OctaveType;

import javax.persistence.*;

@Entity
@Table(name = "octave")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class Octave {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    OctaveType name;

    @JsonGetter("octaveValue") // Имя поля в JSON
    public int getOctaveValue() {
        return name.getValue(); // Возвращаем значение из enum
    }
}

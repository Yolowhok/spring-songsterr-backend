package ru.imit.service.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import ru.imit.service.enums.DurationType;

import javax.persistence.*;

@Entity
@Table(name = "duration")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Data
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class Duration {
    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private DurationType name;
    @JsonGetter("durationValue") // Имя поля в JSON
    public double getDurationValue() {
        return name.getValue(); // Возвращаем значение из enum
    }

}

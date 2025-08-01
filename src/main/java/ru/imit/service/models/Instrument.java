package ru.imit.service.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import ru.imit.service.enums.InstrumentType;

import javax.persistence.*;

@Entity
@Table(name = "instrument", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class Instrument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", length = 30, nullable = false)
    private InstrumentType name;

}




package ru.imit.service.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "tuning")
@Entity
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class Tuning {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private String name;

    @OneToMany(mappedBy = "tuning", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TuningString> tuningStrings;
}
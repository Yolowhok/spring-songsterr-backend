package ru.imit.service.models;


import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "time_signature")
@Entity
public class TimeSignature {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "upper")
    private Integer upper;

    @Column(name = "lower")
    private Integer lower;
}

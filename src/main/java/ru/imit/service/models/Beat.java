package ru.imit.service.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "beat")
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "bar")
@Builder
@Data
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class Beat {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "duration_id")
    Duration duration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bar_id")
    @JsonIgnore
    Bar bar;

    @Column(name = "order_index")
    Integer orderIndex;

    @Builder.Default
    @Column(name = "dotted", nullable = false)
    Boolean dotted = false;

    @Builder.Default
    @Column(name = "rest", nullable = false)
    Boolean rest = false;

    @Column(name = "tuplet_num")
    Integer tupletNum;

    @Column(name = "tuplet_den")
    Integer tupletDen;

    @OneToMany(mappedBy = "beat", cascade = CascadeType.ALL, orphanRemoval = true)
    List<BeatNote> beatNotes;
}

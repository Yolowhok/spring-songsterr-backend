package ru.imit.service.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
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

    @OneToMany(mappedBy = "beat", cascade = CascadeType.ALL, orphanRemoval = true)
    List<BeatNote> beatNotes;
}

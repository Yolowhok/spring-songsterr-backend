package ru.imit.service.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "notesheet")
@Builder
@ToString(exclude = "composition")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class Notesheet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instrument_id")
    private Instrument instrument;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "composition_id")
    @JsonIgnore
    private Composition composition;

    @OneToMany(mappedBy = "notesheet", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("order_index ASC")
    private List<Bar> bars;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tuning_id")
    private Tuning tuning;
    @PostUpdate
    public void postUpdate() {
        this.getComposition().setUpdatedAt(Date.valueOf(LocalDate.now()));
    }
}


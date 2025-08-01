package ru.imit.service.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "bar")
@ToString(exclude = "notesheet")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class Bar {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "temp_in_bpm")
    private Integer tempInBpm;

    @ManyToOne
    @JoinColumn(name = "time_signature_id")
//    @JsonIgnore
    private TimeSignature timeSignature;

    @ManyToOne
    @JoinColumn(name = "notesheet_id")
    @JsonIgnore
    private Notesheet notesheet;

    @Column(name = "order_index")
    private Integer orderIndex;

    @OneToMany(mappedBy = "bar", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("order_index ASC")
    private List<Beat> beats;
}

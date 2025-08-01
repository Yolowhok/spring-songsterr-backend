package ru.imit.service.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "composition")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class Composition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "band")
    private String band;
    @Column(name = "title")
    private String title;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAt;
    @OneToMany(mappedBy = "composition", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Notesheet> notesheets;
    @PrePersist
    public void prePersist() {
        this.createdAt = Date.valueOf(LocalDate.now());
    }
    @PostUpdate
    public void postUpdate() {
        this.updatedAt = Date.valueOf(LocalDate.now());
    }
}



//    @OneToMany(mappedBy = "composition", cascade = CascadeType.REMOVE, orphanRemoval = true)

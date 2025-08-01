package ru.imit.service.dto;

import lombok.*;
import ru.imit.service.models.Notesheet;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CompositionDTO {
    private Long id;
    private String band;
    private String title;
    private Date createdAt;
    private Date updatedAt;
}

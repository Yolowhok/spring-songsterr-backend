package ru.imit.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import ru.imit.service.models.Bar;
import ru.imit.service.models.Composition;
import ru.imit.service.models.Instrument;
import ru.imit.service.models.Tuning;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class NotesheetDTO {
    private Long id;
    private Instrument instrument;
    private Tuning tuning;
}
package ru.imit.service.dto;

import lombok.*;
import ru.imit.service.models.Composition;
import ru.imit.service.models.Instrument;
import ru.imit.service.models.Tuning;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class NotesheetCreateDTO {
    Instrument instrument;
    Tuning tuning;
    Long compositionID;

}

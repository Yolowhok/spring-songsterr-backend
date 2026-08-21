package ru.imit.service.composition.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddBarRequest {
    /** "left" or "right" relative to atOrderIndex */
    private String side;
    private Integer atOrderIndex;
    private Integer tempInBpm;
}

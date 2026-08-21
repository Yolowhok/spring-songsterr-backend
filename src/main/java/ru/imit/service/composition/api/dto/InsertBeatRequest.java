package ru.imit.service.composition.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InsertBeatRequest {
    /**
     * Insert after this beat orderIndex.
     * Use 0 (or null) to insert at the start (new beat gets orderIndex 1).
     */
    private Integer afterBeatOrder;
}

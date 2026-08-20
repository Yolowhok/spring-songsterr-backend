package ru.imit.service.composition.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.imit.service.models.*;

import java.sql.Date;
import java.util.List;

/**
 * Write DTO for save/update composition.
 * Same JSON shape the frontend sends today; never exposed as a JPA entity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompositionSaveRequest {
    private Long id;
    private String band;
    private String title;
    private Date createdAt;
    private Date updatedAt;
    private List<Notesheet> notesheets;
}

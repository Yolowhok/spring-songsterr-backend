package ru.imit.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.imit.service.models.Composition;
import ru.imit.service.models.Notesheet;

import java.util.List;

@Repository
public interface NotesheetRepository extends JpaRepository<Notesheet, Long> {

    @Query(value = "SELECT * FROM Notesheet n WHERE n.composition_id = :id",  nativeQuery = true)
    List<Notesheet> getNotesheetsById(@Param("id") Long id);


}

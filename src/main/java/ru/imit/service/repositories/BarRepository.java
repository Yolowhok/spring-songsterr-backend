package ru.imit.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.imit.service.models.Bar;

@Repository
public interface BarRepository extends JpaRepository<Bar, Long> {

}

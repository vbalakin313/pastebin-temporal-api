package ru.balakinvv.pastebintemporalapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.balakinvv.pastebintemporalapi.entities.PasteEntity;

public interface PasteRepository extends JpaRepository<PasteEntity, Long> {
}

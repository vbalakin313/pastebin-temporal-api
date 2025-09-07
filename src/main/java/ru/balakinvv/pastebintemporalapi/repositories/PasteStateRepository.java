package ru.balakinvv.pastebintemporalapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.balakinvv.pastebintemporalapi.entities.PasteStateEntity;

public interface PasteStateRepository extends JpaRepository<PasteStateEntity, Long> {
}

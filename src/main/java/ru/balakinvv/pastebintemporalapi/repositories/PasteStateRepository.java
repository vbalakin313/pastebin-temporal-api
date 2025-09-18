package ru.balakinvv.pastebintemporalapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.balakinvv.pastebintemporalapi.entities.PasteStateEntity;

import java.util.Optional;

public interface PasteStateRepository extends JpaRepository<PasteStateEntity, Long> {

    @Query("SELECT ps FROM PasteStateEntity ps LEFT JOIN FETCH ps.pastes WHERE ps.id = :stateId")
    Optional<PasteStateEntity> findByIdWithPastes(@Param("stateId") Long stateId);
}

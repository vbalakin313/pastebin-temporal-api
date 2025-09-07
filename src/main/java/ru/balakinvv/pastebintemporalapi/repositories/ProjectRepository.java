package ru.balakinvv.pastebintemporalapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.balakinvv.pastebintemporalapi.entities.ProjectEntity;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
}

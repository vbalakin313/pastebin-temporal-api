package ru.balakinvv.pastebintemporalapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.balakinvv.pastebintemporalapi.entities.ProjectEntity;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
    Stream <ProjectEntity> streamAllByTitleStartsWithIgnoreCase(String s);
    Stream<ProjectEntity> streamAllBy();

    Optional<ProjectEntity> findByTitle(String title);
}

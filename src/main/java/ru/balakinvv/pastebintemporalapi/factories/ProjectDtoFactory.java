package ru.balakinvv.pastebintemporalapi.factories;

import org.springframework.stereotype.Component;
import ru.balakinvv.pastebintemporalapi.dto.ProjectDto;
import ru.balakinvv.pastebintemporalapi.entities.ProjectEntity;

@Component
public class ProjectDtoFactory {
    public ProjectDto createProjectDto(ProjectEntity projectEntity) {
        return ProjectDto.builder()
                .id(projectEntity.getId())
                .title(projectEntity.getTitle())
                .createdAt(projectEntity.getCreatedAt())
                .updatedAt(projectEntity.getUpdatedAt())
                .build();
    }
}

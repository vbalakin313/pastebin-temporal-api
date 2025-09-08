package ru.balakinvv.pastebintemporalapi.controllers;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.balakinvv.pastebintemporalapi.dto.ProjectDto;
import ru.balakinvv.pastebintemporalapi.entities.ProjectEntity;
import ru.balakinvv.pastebintemporalapi.exceptions.BadRequestException;
import ru.balakinvv.pastebintemporalapi.factories.ProjectDtoFactory;
import ru.balakinvv.pastebintemporalapi.repositories.ProjectRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Transactional
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class ProjectController {

    private ProjectRepository projectRepository;

    private ProjectDtoFactory projectDtoFactory;

    private static final String FETCH_PROJECTS = "/v1/projects";
    private static final String CREATE_PROJECT = "/v1/projects/create";
    private static final String UPDATE_PROJECTS = "/v1/projects/update";
    private static final String DELETE_PROJECT = "/v1/projects/{projectId}";

    @GetMapping(FETCH_PROJECTS)
    public List<ProjectDto> getAllProjects(@RequestParam(name = "prefix_title", required = false) Optional<String> optionalPrefixTitle) {

        optionalPrefixTitle = optionalPrefixTitle.filter(s -> !s.trim().isEmpty());

        Stream<ProjectEntity> projectStream = optionalPrefixTitle
                .map(prefix -> projectRepository.streamAllByTitleStartsWithIgnoreCase(prefix))
                .orElseGet(projectRepository::streamAllBy);

        return projectStream
                .map(projectDtoFactory::createProjectDto)
                .collect(Collectors.toList());


    }

    @PutMapping(CREATE_PROJECT)
    public ProjectDto createProject(@RequestParam(name = "project_title") String projectTitle) {

        if (projectTitle.isEmpty() || projectTitle.trim().isEmpty()) {
            throw new BadRequestException("Project title cannot be empty");
        }

        projectRepository.findByTitle(projectTitle).ifPresent(project -> {
            throw new BadRequestException(String.format("Project %s already exists", projectTitle));
        });

        ProjectEntity newProject = ProjectEntity.builder()
                .title(projectTitle)
                .build();

        projectRepository.saveAndFlush(newProject);

        return projectDtoFactory.createProjectDto(newProject);
    }
}

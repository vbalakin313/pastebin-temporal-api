package ru.balakinvv.pastebintemporalapi.controllers;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.balakinvv.pastebintemporalapi.dto.PasteStateDto;
import ru.balakinvv.pastebintemporalapi.entities.PasteStateEntity;
import ru.balakinvv.pastebintemporalapi.entities.ProjectEntity;
import ru.balakinvv.pastebintemporalapi.exceptions.BadRequestException;
import ru.balakinvv.pastebintemporalapi.factories.PasteStateDtoFactory;
import ru.balakinvv.pastebintemporalapi.repositories.PasteStateRepository;
import ru.balakinvv.pastebintemporalapi.repositories.ProjectRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class PasteStateController {

    private final ProjectRepository projectRepository;
    private final PasteStateRepository pasteStateRepository;
    private final PasteStateDtoFactory pasteStateDtoFactory;

    private static final String CREATE_PASTE_STATE = "/v1/projects/{projectId}/states";
    private static final String GET_STATE = "/v1/states/{stateId}";
    private static final String UPDATE_STATE = "/v1/states/{stateId}";
    private static final String DELETE_PASTE_STATE = "/v1/states/{stateId}";

    @PutMapping(CREATE_PASTE_STATE)
    public PasteStateDto createPasteState(@PathVariable Long projectId,
                                          @RequestParam(name = "state_title", required = false) String title,
                                          @RequestParam(name = "state_description", required = false) String description,
                                          @RequestParam(name = "state_category", required = false) String category,
                                          @RequestParam(name = "state_tags", required = false) List<String> tags) {

        ProjectEntity projectEntity = projectRepository.findById(projectId).orElseThrow(
                () -> new BadRequestException("Project %s not found")
        );

        PasteStateEntity pasteStateEntity = PasteStateEntity.builder()
                .title(title)
                .description(description)
                .category(category)
                .tags(tags)
                .build();

        projectEntity.setPasteStates(Collections.singletonList(pasteStateEntity));

        pasteStateRepository.save(pasteStateEntity);
        return pasteStateDtoFactory.createPasteStateDto(pasteStateEntity);
    }
}

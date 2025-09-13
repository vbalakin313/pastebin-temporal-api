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
                () -> new BadRequestException(String.format("State %s not found", projectId))
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

    @GetMapping(GET_STATE)
    public PasteStateDto getPastState(@PathVariable Long stateId){

        PasteStateEntity pasteState = pasteStateRepository.findById(stateId).orElseThrow(
                () -> new BadRequestException(String.format("State %s not found", stateId))
        );

        return pasteStateDtoFactory.createPasteStateDto(pasteState);
    }

    @PutMapping(UPDATE_STATE)
    public PasteStateDto updatePasteState(@PathVariable Long stateId,
                                          @RequestParam(name = "title_state", required = false) String title,
                                          @RequestParam(name = "description_state", required = false) String description,
                                          @RequestParam(name = "category_state", required = false) String category,
                                          @RequestParam(name = "tags_state" ,required = false) List<String> tags) {

        PasteStateEntity pasteState = pasteStateRepository.findById(stateId).orElseThrow(
                () -> new BadRequestException(String.format("State %s not found", stateId))
        );

        pasteState.setTitle(title);
        pasteState.setDescription(description);
        pasteState.setCategory(category);
        pasteState.setTags(tags);

        pasteStateRepository.saveAndFlush(pasteState);

        return pasteStateDtoFactory.createPasteStateDto(pasteState);

    }

    @DeleteMapping(DELETE_PASTE_STATE)
    public String deletePasteState(@PathVariable Long stateId) {

        PasteStateEntity pasteState = pasteStateRepository.findById(stateId).orElseThrow(
                () -> new BadRequestException(String.format("State %s not found", stateId))
        );

        pasteStateRepository.delete(pasteState);
        return String.format("Paste state %s deleted", pasteState.getId());
    }
}

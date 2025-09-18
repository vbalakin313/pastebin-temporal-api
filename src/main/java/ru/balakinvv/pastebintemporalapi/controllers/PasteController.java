package ru.balakinvv.pastebintemporalapi.controllers;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.balakinvv.pastebintemporalapi.dto.PasteDto;
import ru.balakinvv.pastebintemporalapi.entities.PasteEntity;
import ru.balakinvv.pastebintemporalapi.entities.PasteStateEntity;
import ru.balakinvv.pastebintemporalapi.entities.ProjectEntity;
import ru.balakinvv.pastebintemporalapi.exceptions.BadRequestException;
import ru.balakinvv.pastebintemporalapi.factories.PasteDtoFactory;
import ru.balakinvv.pastebintemporalapi.repositories.PasteRepository;
import ru.balakinvv.pastebintemporalapi.repositories.PasteStateRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Transactional
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class PasteController {

    private final PasteStateRepository pasteStateRepository;
    private final PasteRepository pasteRepository;
    private final PasteDtoFactory pasteDtoFactory;

    private static final String ALL_PASTES_BY_STATE = "/v1/states/{stateId}/pastes";
    private static final String CREATE_PASTE_IN_STATE = "/v1/states/{stateId}/pastes";
    private static final String GET_PASTE = "/v1/pastes/{pasteId}";
    private static final String UPDATE_PASTE = "/v1/pastes/{pasteId}";
    private static final String DELETE_PASTE = "/v1/pastes/{pasteId}";

    @PutMapping(CREATE_PASTE_IN_STATE)
    public PasteDto createPaste(@PathVariable Long stateId,
                                @RequestParam(name = "paste_title", required = false) String title,
                                @RequestParam(name = "paste_description", required = false) String description) {

        PasteStateEntity pasteStateEntity = pasteStateRepository.findById(stateId).orElseThrow(
                () -> new BadRequestException(String.format("State %s not found", stateId))
        );

        PasteEntity pasteEntity = PasteEntity.builder()
                .title(title)
                .description(description)
                .state(pasteStateEntity)
                .build();

        pasteStateEntity.setPastes(Collections.singletonList(pasteEntity));

        pasteRepository.saveAndFlush(pasteEntity);

        return pasteDtoFactory.createPasteDto(pasteEntity);
    }

    @GetMapping(ALL_PASTES_BY_STATE)
    public List<PasteDto> getAllPastes(@PathVariable Long stateId) {

        PasteStateEntity pasteStateEntity = pasteStateRepository.findByIdWithPastes(stateId).orElseThrow(
                () -> new BadRequestException(String.format("State %s not found", stateId))
        );

        List<PasteEntity> pasteEntities = pasteStateEntity.getPastes();

        return pasteEntities.stream().map(pasteDtoFactory::createPasteDto).collect(Collectors.toList());
    }

    @GetMapping(GET_PASTE)
    public PasteDto getPaste(@PathVariable Long pasteId) {

        PasteEntity pasteEntity = pasteRepository.findById(pasteId).orElseThrow(
                () -> new BadRequestException(String.format("Paste %s not found", pasteId))
        );

        return pasteDtoFactory.createPasteDto(pasteEntity);
    }

    @PutMapping(UPDATE_PASTE)
    public PasteDto updatePaste(@PathVariable Long pasteId,
                                @RequestParam(name = "paste_title", required = false) String title,
                                @RequestParam(name = "paste_description", required = false) String description){

        PasteEntity pasteEntity = pasteRepository.findById(pasteId).orElseThrow(
                () -> new BadRequestException(String.format("Paste %s not found", pasteId))
        );

        pasteEntity.setTitle(title);
        pasteEntity.setDescription(description);
        pasteEntity = pasteRepository.saveAndFlush(pasteEntity);

        return pasteDtoFactory.createPasteDto(pasteEntity);
    }

    @DeleteMapping(DELETE_PASTE)
    public String deletePaste(@PathVariable Long pasteId) {
        PasteEntity pasteEntity = pasteRepository.findById(pasteId).orElseThrow(
                () -> new BadRequestException(String.format("Paste %s not found", pasteId))
        );

        pasteRepository.delete(pasteEntity);

        return String.format("Paste %s deleted", pasteId);
    }

}

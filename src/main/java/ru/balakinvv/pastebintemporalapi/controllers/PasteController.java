package ru.balakinvv.pastebintemporalapi.controllers;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.balakinvv.pastebintemporalapi.dto.PasteDto;
import ru.balakinvv.pastebintemporalapi.entities.PasteEntity;
import ru.balakinvv.pastebintemporalapi.entities.PasteStateEntity;
import ru.balakinvv.pastebintemporalapi.exceptions.BadRequestException;
import ru.balakinvv.pastebintemporalapi.factories.PasteDtoFactory;
import ru.balakinvv.pastebintemporalapi.repositories.PasteRepository;
import ru.balakinvv.pastebintemporalapi.repositories.PasteStateRepository;

import java.util.Collections;

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
                .build();

        pasteStateEntity.setPastes(Collections.singletonList(pasteEntity));

        pasteRepository.saveAndFlush(pasteEntity);

        return pasteDtoFactory.createPasteDto(pasteEntity);
    }
}

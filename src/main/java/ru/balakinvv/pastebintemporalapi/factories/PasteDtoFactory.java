package ru.balakinvv.pastebintemporalapi.factories;

import org.springframework.stereotype.Component;
import ru.balakinvv.pastebintemporalapi.dto.PasteDto;
import ru.balakinvv.pastebintemporalapi.entities.PasteEntity;

@Component
public class PasteDtoFactory {

    public PasteDto createPasteDto(PasteEntity pasteEntity) {
        return PasteDto.builder()
                .id(pasteEntity.getId())
                .title(pasteEntity.getTitle())
                .description(pasteEntity.getDescription())
                .createdAt(pasteEntity.getCreatedAt())
                .stateId(pasteEntity.getState().getId())
                .build();
    }
}

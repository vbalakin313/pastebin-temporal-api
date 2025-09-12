package ru.balakinvv.pastebintemporalapi.factories;

import org.springframework.stereotype.Component;
import ru.balakinvv.pastebintemporalapi.dto.PasteStateDto;
import ru.balakinvv.pastebintemporalapi.entities.PasteStateEntity;

@Component
public class PasteStateDtoFactory {

    public PasteStateDto createPasteStateDto(PasteStateEntity pasteStateEntity) {
        return PasteStateDto.builder()
                .id(pasteStateEntity.getId())
                .title(pasteStateEntity.getTitle())
                .description(pasteStateEntity.getDescription())
                .category(pasteStateEntity.getCategory())
                .createdAt(pasteStateEntity.getCreatedAt())
                .tags(pasteStateEntity.getTags())
                .build();
    }
}

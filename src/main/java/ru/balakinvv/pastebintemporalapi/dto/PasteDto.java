package ru.balakinvv.pastebintemporalapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import ru.balakinvv.pastebintemporalapi.entities.PasteStateEntity;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasteDto {

    @NonNull
    private Long id;

    @NonNull
    private String title;

    @NonNull
    private String description;

    @NonNull
    @JsonProperty("created_at")
    private Instant createdAt = Instant.now();

    @NonNull
    private Long stateId;
}

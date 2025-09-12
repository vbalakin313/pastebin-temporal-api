package ru.balakinvv.pastebintemporalapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasteStateDto {

    @NonNull
    private Long id;

    @NonNull
    private String title;

    @NonNull
    private String description;

    @NonNull
    private String category;

    @NonNull
    private List<String> tags;

    @NonNull
    @JsonProperty("created_at")
    private Instant createdAt = Instant.now();
}

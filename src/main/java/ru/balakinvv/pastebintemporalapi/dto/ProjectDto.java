package ru.balakinvv.pastebintemporalapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {

    @NonNull
    private Long id;

    @NonNull
    private String title;

    @NonNull
    @JsonProperty("created_at")
    private Instant createdAt;

    @NonNull
    @JsonProperty("updated_at")
    private Instant updatedAt;
}

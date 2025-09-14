package ru.balakinvv.pastebintemporalapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

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
}

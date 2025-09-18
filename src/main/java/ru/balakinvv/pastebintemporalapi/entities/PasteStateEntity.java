package ru.balakinvv.pastebintemporalapi.entities;


import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "paste_states")
public class PasteStateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String title;

    private String description;

    private String category;

    private List<String> tags;

    @Builder.Default
    private Instant createdAt = Instant.now();

    @Builder.Default
    @OneToMany(mappedBy = "state", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PasteEntity> pastes =  new ArrayList<>();

}

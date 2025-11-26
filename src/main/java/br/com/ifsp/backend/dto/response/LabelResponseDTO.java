package br.com.ifsp.backend.dto.response;

import br.com.ifsp.backend.model.catalog.Label;

import java.time.LocalDate;

public record LabelResponseDTO(
        Long id,
        String name,
        LocalDate foundationDate,
        LocalDate endDate,
        String bio
) {
    public LabelResponseDTO(Label label) {
        this(
                label.getId(),
                label.getName(),
                label.getFoundationDate(),
                label.getEndDate(),
                label.getBio()
        );
    }
}

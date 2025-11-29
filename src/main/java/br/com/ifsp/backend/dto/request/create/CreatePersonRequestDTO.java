package br.com.ifsp.backend.dto.request.create;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record CreatePersonRequestDTO(
        @NotBlank String name,
        String description,
        String imageUrl,
        Long countryId,
        LocalDate birthDate,
        LocalDate deathDate
) {
}

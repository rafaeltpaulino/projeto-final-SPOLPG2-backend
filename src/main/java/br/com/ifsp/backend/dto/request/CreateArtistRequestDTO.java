package br.com.ifsp.backend.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record CreateArtistRequestDTO(
        @NotBlank(message = "O nome do artista é obrigatório.")
        String name,
        @NotBlank(message = "A descrição é obrigatória.")
        String description,
        String imageUrl,
        @NotBlank(message = "A data de inicio das atividades é obrigatória.")
        LocalDate startDate,
        LocalDate endDate,
        @NotBlank(message = "O país é obrigatório.")
        Long countryId
) {
}

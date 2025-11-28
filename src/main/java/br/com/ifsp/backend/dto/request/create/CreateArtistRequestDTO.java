package br.com.ifsp.backend.dto.request.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateArtistRequestDTO(
        @NotBlank(message = "O nome do artista é obrigatório.")
        String name,
        @NotBlank(message = "A descrição é obrigatória.")
        String description,
        String imageUrl,
        @NotNull(message = "A data de inicio das atividades é obrigatória.")
        LocalDate startDate,
        LocalDate endDate,
        @NotNull(message = "O país é obrigatório.")
        Long countryId
) {
}

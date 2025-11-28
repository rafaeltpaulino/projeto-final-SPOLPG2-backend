package br.com.ifsp.backend.dto.request.patch;

import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record PatchArtistRequestDTO(
        @Size(min = 1, max = 100, message = "O nome deve ter entre 1 e 100 caracteres")
        String name,
        String description,
        String imageUrl,
        LocalDate startDate,
        LocalDate endDate,
        Long countryId
) {
}

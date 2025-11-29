package br.com.ifsp.backend.dto.request.patch;

import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UpdateArtistRequestDTO(
        @Size(min = 1, max = 100)
        String name,

        @Size(max = 2000)
        String description,

        String imageUrl,

        Long countryId,

        // Campos Específicos de Pessoa
        LocalDate birthDate,
        LocalDate deathDate,

        // Campos Específicos de Banda
        LocalDate formationDate,
        LocalDate endDate
) {}

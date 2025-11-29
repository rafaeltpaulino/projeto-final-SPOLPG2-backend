package br.com.ifsp.backend.dto.request.patch;

import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Set;

public record UpdateMasterRequestDTO(
        @Size(min = 1, max = 100)
        String title,

        Integer releaseYear,

        String coverImageUrl,

        @Size(max = 2000)
        String description,

        // Se enviar null, não mexe. Se enviar lista vazia [], remove todos.
        Set<Long> artistsId,
        Set<Long> genresId
) {}

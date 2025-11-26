package br.com.ifsp.backend.dto.response;

import br.com.ifsp.backend.model.catalog.Genre;
import lombok.Builder;

@Builder
public record GenreResponseDTO(
        Long id,
        String name)
{
    public GenreResponseDTO(Genre genre) {
        this(
                genre.getId(),
                genre.getName()
        );
    }
}

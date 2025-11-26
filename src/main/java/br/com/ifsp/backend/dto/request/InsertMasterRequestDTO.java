package br.com.ifsp.backend.dto.request;

import br.com.ifsp.backend.model.catalog.Artist;
import br.com.ifsp.backend.model.catalog.Genre;

import java.util.Set;

public record InsertMasterRequestDTO(
        String title,
        Integer releaseYear,
        String coverImageUrl,
        String description,
        Set<Artist> artists,
        Set<Genre> genres
) {
}

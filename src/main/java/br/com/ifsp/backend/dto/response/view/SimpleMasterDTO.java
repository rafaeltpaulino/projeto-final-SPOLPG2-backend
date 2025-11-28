package br.com.ifsp.backend.dto.response.view;

import br.com.ifsp.backend.model.catalog.Artist;
import br.com.ifsp.backend.model.catalog.Genre;
import br.com.ifsp.backend.model.catalog.Master;

import java.util.Set;

public record SimpleMasterDTO(
        Long id,
        String title,
        Integer releaseYear,
        String coverImageUrl,
        Double averageRating,
        Set<Artist> artists,
        Set<Genre> genres
) {
    public SimpleMasterDTO(Master master) {
        this(
                master.getId(),
                master.getTitle(),
                master.getReleaseYear(),
                master.getCoverImageUrl(),
                master.getAverageRating(),
                master.getArtists(),
                master.getGenres()
        );
    }
}

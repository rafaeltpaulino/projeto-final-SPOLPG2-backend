package br.com.ifsp.backend.dto.response;

import br.com.ifsp.backend.model.catalog.Artist;
import br.com.ifsp.backend.model.catalog.Genre;
import br.com.ifsp.backend.model.catalog.Master;
import lombok.Builder;

import java.util.Set;

public record MasterResponseDTO(
        Long id,
        String title,
        Integer releaseYear,
        String coverImageUrl,
        Double averageRating,
        String description,
        Set<Artist> artists,
        Set<Genre> genres
) {

    public MasterResponseDTO(Master master) {
        this(
                master.getId(),
                master.getTitle(),
                master.getReleaseYear(),
                master.getCoverImageUrl(),
                master.getAverageRating(),
                master.getDescription(),
                master.getArtists(),
                master.getGenres()
        );
    }
}

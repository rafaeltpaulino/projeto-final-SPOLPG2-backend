package br.com.ifsp.backend.dto.response.view;

import br.com.ifsp.backend.model.catalog.Artist;
import br.com.ifsp.backend.model.catalog.Genre;
import br.com.ifsp.backend.model.catalog.Master;
import br.com.ifsp.backend.model.catalog.Release;

import java.util.List;
import java.util.Set;

public record MasterResponseDTO(
        Long id,
        String title,
        Integer releaseYear,
        String coverImageUrl,
        Double averageRating,
        String description,
        Set<Artist> artists,
        Set<Genre> genres,
        List<Release> releases
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
                master.getGenres(),
                master.getReleases()
        );
    }
}

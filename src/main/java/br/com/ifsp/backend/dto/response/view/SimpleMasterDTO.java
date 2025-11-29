package br.com.ifsp.backend.dto.response.view;

import br.com.ifsp.backend.model.catalog.Artist;
import br.com.ifsp.backend.model.catalog.Genre;
import br.com.ifsp.backend.model.catalog.Master;

import java.util.List;
import java.util.Set;

public record SimpleMasterDTO(
        Long id,
        String title,
        Integer releaseYear,
        String coverImageUrl,
        Double averageRating,
        List<String> artists,
        List<String> genres
) {
    public SimpleMasterDTO(Master master) {
        this(
                master.getId(),
                master.getTitle(),
                master.getReleaseYear(),
                master.getCoverImageUrl(),
                master.getAverageRating(),
                master.getArtists().stream().map(Artist::getName).toList(),
                master.getGenres().stream().map(Genre::getName).toList()
        );
    }
}

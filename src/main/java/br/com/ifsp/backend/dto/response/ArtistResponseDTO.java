package br.com.ifsp.backend.dto.response;

import br.com.ifsp.backend.model.catalog.Artist;

import java.time.LocalDate;

public record ArtistResponseDTO(
        Long id,
        String name,
        String description,
        String imageUrl,
        LocalDate startDate,
        LocalDate endDate,
        String countryName
) {

    public ArtistResponseDTO(Artist artist) {
        this(
                artist.getId(),
                artist.getName(),
                artist.getDescription(),
                artist.getImageUrl(),
                artist.getStartDate(),
                artist.getEndDate(),
                artist.getCountry().getName()
        );
    }
}

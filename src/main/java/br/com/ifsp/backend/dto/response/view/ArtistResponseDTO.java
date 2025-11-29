package br.com.ifsp.backend.dto.response.view;

import br.com.ifsp.backend.model.catalog.Artist;

import java.time.LocalDate;

public record ArtistResponseDTO(
        Long id,
        String name,
        String description,
        String imageUrl,
        String countryName
) {

    public ArtistResponseDTO(Artist artist) {
        this(
                artist.getId(),
                artist.getName(),
                artist.getDescription(),
                artist.getImageUrl(),
                artist.getCountry().getName()
        );
    }
}

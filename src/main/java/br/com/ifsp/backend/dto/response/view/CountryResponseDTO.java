package br.com.ifsp.backend.dto.response.view;

import br.com.ifsp.backend.model.Country;

public record CountryResponseDTO(
        Long id,
        String name
) {

    public CountryResponseDTO(Country country) {
        this(
                country.getId(),
                country.getName()
        );
    }
}

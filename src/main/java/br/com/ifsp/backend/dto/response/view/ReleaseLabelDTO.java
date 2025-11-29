package br.com.ifsp.backend.dto.response.view;

import br.com.ifsp.backend.model.catalog.ReleaseLabel;

public record ReleaseLabelDTO(
        String labelName,
        String catalogNumber,
        String role
) {

    public ReleaseLabelDTO(ReleaseLabel releaseLabel) {
        this(
                releaseLabel.getLabel().getName(),
                releaseLabel.getCatalogNumber(),
                releaseLabel.getRole()
        );
    }
}

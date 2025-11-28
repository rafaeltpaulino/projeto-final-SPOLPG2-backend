package br.com.ifsp.backend.dto.response.view;

import br.com.ifsp.backend.model.Country;
import br.com.ifsp.backend.model.catalog.Release;
import br.com.ifsp.backend.model.catalog.ReleaseLabel;
import br.com.ifsp.backend.model.catalog.Track;

import java.time.LocalDate;
import java.util.List;

public record ReleaseResponseDTO(
        Long id,
        String title,
        LocalDate releaseDate,
        String format,
        String barcode,
        boolean main,
        SimpleMasterDTO master,
        Country country,
        List<ReleaseLabel> labels,
        List<Track> tracks
) {
    public ReleaseResponseDTO(Release release) {
        this(
                release.getId(),
                release.getTitle(),
                release.getReleaseDate(),
                release.getFormat(),
                release.getBarcode(),
                release.isMain(),
                new SimpleMasterDTO(release.getMaster()),
                release.getCountry(),
                release.getLabels(),
                release.getTracks()
        );
    }
}

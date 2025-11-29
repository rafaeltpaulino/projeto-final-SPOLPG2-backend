package br.com.ifsp.backend.dto.response.view;

import br.com.ifsp.backend.model.catalog.Track;

public record SimpleTrackDTO(
        String title,
        String position,
        Integer durationSeconds
) {
    public SimpleTrackDTO(Track track) {
        this(
                track.getTitle(),
                track.getPosition(),
                track.getDurationSeconds()
        );
    }
}

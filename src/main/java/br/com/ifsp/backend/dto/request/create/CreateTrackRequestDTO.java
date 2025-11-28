package br.com.ifsp.backend.dto.request.create;

public record CreateTrackRequestDTO(
        String title,
        String position,
        Integer durationSeconds
) {
}

package br.com.ifsp.backend.dto.request;

public record CreateTrackRequestDTO(
        String title,
        String position,
        Integer durationSeconds
) {
}

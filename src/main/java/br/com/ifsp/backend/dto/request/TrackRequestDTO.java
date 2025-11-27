package br.com.ifsp.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TrackRequestDTO(
        @NotBlank(message = "O título é obrigatório.")
        String title,
        @NotBlank(message = "A posição da música é obrigatória.")
        String position,
        @NotNull(message = "A duração da música é obrigatória.")
        Integer durationSeconds
) {
}

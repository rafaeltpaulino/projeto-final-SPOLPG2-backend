package br.com.ifsp.backend.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateGenreRequestDTO(
        @NotBlank(message = "O nome do gênero musical é obrigatório.")
        String name
) {
}

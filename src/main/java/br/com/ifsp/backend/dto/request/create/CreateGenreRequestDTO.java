package br.com.ifsp.backend.dto.request.create;

import jakarta.validation.constraints.NotBlank;

public record CreateGenreRequestDTO(
        @NotBlank(message = "O nome do gênero musical é obrigatório.")
        String name
) {
}

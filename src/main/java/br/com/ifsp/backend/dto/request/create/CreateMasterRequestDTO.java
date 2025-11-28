package br.com.ifsp.backend.dto.request.create;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record CreateMasterRequestDTO(
        @NotBlank(message = "O título é obrigatório.")
        String title,
        @NotNull(message = "O ano de lançamento é obrigatório.")
        @DecimalMax(value = "10000", message = "O ano lançamento deve ter no máximo 4 dígitos.")
        Integer releaseYear,
        String coverImageUrl,
        @NotBlank(message = "A descrição é obrigatória.")
        String description,
        @NotEmpty(message = "É necessário informar pelo menos um artista.")
        Set<Long> artistsId,
        @NotEmpty(message = "É necessário informar pelo menos um gênero musical.")
        Set<Long> genresId
) {
}

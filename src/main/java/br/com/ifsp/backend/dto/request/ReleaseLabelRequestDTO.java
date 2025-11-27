package br.com.ifsp.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReleaseLabelRequestDTO(
        @NotNull(message = "O id da gravadora é obrigatório.")
        Long labelId,
        @NotBlank(message = "O número de catálogo é obrigatório.")
        String catalogNumber,
        @NotBlank(message = "O tipo da gravadora é obrigatório.")
        String role // Ex: "Main Label", "Distributor"
) {
}

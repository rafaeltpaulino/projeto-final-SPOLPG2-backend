package br.com.ifsp.backend.dto.request.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateLabelRequestDTO(
        @NotBlank(message = "O nome é obrigátorio.")
        String name,
        @NotNull(message = "A data de fundação é obrigatória.")
        LocalDate foundationDate,
        LocalDate endDate,
        String bio
) {
}

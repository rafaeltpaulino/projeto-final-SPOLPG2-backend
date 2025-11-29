package br.com.ifsp.backend.dto.request.patch;

import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UpdateLabelRequestDTO(
        @Size(min = 1, max = 100, message = "O nome deve ter entre 1 e 100 caracteres")
        String name,

        LocalDate foundationDate,

        LocalDate endDate,

        @Size(max = 2000, message = "A bio deve ter no máximo 2000 caracteres")
        String bio
) {}
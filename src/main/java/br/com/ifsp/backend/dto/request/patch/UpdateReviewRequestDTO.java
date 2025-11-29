package br.com.ifsp.backend.dto.request.patch;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record UpdateReviewRequestDTO(
        @Min(value = 0, message = "A nota mínima é 0")
        @Max(value = 5, message = "A nota máxima é 5")
        Integer rating,

        @Size(max = 1000, message = "O comentário deve ter no máximo 1000 caracteres")
        String comment
) {}

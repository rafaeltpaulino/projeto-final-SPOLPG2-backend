package br.com.ifsp.backend.dto.request.patch;

import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UpdateCollectionItemRequestDTO(
        LocalDate acquiredDate,

        // Recebemos como String para fazer a conversão segura no Service (MINT, VG, etc.)
        String mediaCondition,
        String sleeveCondition,

        @Size(max = 1000, message = "A nota privada deve ter no máximo 1000 caracteres")
        String privateNotes
) {}
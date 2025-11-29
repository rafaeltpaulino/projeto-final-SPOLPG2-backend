package br.com.ifsp.backend.dto.request.create;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.List;

public record CreateGroupRequestDTO(
        @NotBlank String name,
        String description,
        Long countryId,
        LocalDate formationDate,
        List<MemberBindingDTO> members
) {
}

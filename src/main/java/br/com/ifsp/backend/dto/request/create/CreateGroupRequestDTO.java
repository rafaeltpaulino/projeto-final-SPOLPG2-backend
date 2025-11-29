package br.com.ifsp.backend.dto.request.create;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public record CreateGroupRequestDTO(
        @NotBlank
        String name,
        String description,
        String imageUrl,
        Long countryId,
        LocalDate formationDate,
        LocalDate endDate,
        List<MemberBindingDTO> members
) {

    public CreateGroupRequestDTO {
        members = (members == null) ? Collections.emptyList() : members;
    }
}

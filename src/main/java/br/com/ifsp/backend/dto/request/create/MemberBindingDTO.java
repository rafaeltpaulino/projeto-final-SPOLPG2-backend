package br.com.ifsp.backend.dto.request.create;

import java.time.LocalDate;

public record MemberBindingDTO(
        Long personArtistId, // Usado se já existe
        String role,
        LocalDate joinDate,
        boolean active
) {
}

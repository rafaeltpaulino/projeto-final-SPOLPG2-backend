package br.com.ifsp.backend.dto.request.patch;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record PatchUserRequestDTO(
        @Size(min = 2, max = 100, message = "O nome deve ter entre 2 e 100 caracteres.")
        String firstName,
        @Size(min = 2, max = 100, message = "O sobrenome deve ter entre 2 e 100 caracteres.")
        String lastName,
        LocalDate birthdate,
        @Size(max = 500)
        String bio,
        Long countryId
) {
}

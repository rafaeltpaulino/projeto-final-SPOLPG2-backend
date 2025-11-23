package br.com.ifsp.backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserLoginRequestDTO(
        @NotBlank(message = "O email é obrigatório.")
        @Email(message = "O email enviado é inválido.")
        String email,

        @NotBlank(message = "A senha é obrigatória")
        String password
) {
}

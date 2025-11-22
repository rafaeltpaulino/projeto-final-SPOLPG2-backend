package br.com.ifsp.backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserLoginRequestDTO(
        @NotBlank(message = "O nome de usuário é obrigatório.")
        @Size(min = 5, max = 25, message = "O nome de usuário deve ter entre 5 e 25 caracteres.")
        String username,

        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 5, max = 30, message = "A senha deve ter entre 5 e 30 caracteres")
        String password
) {
}

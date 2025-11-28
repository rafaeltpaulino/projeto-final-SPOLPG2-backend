package br.com.ifsp.backend.dto.request.create;
import jakarta.validation.constraints.*;

public record RegisterUserRequestDTO(
        @NotBlank(message = "O nome de usuário é obrigatório.")
        @Size(min = 5, max = 25, message = "O nome de usuário deve ter entre 5 e 25 caracteres.")
        String username,

        @NotBlank(message = "O email é obrigatório.")
        @Email(message = "O email informado é inválido.")
        @Size(min = 5, max = 50, message = "O email deve ter entre 5 e 50 caracteres.")
        String email,

        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 5, max = 30, message = "A senha deve ter entre 5 e 30 caracteres")
        String password
) {
}

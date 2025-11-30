package br.com.ifsp.backend.dto.response.view;

public record UserLoginResponseDTO(String token, Long userId, String username) {
}

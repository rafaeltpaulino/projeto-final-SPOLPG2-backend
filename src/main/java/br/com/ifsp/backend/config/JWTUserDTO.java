package br.com.ifsp.backend.config;

import lombok.Builder;

@Builder
public record JWTUserDTO(Long userID, String email) {
}

package br.com.ifsp.backend.config;

import lombok.Builder;

import java.util.List;

@Builder
public record JWTUserDTO(Long userID, String email, List<String> roles) {
}

package br.com.ifsp.backend.dto.response.view;

import br.com.ifsp.backend.model.user.Role;

import java.util.List;
import java.util.Set;

public record UserLoginResponseDTO(String token, Long userId, String username, Set<Role> roles) {
}

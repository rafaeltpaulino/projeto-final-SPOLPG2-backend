package br.com.ifsp.backend.dto.response.view;

import br.com.ifsp.backend.model.Country;
import br.com.ifsp.backend.model.social.CollectionItem;
import br.com.ifsp.backend.model.social.Review;
import br.com.ifsp.backend.model.user.Role;
import br.com.ifsp.backend.model.user.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public record UserResponseDTO(
        Long id,
        String username,
        String email,
        String firstName,
        String lastName,
        LocalDate birthdate,
        String countryName,
        LocalDateTime createdAt,
        String bio,
        List<String> roles
) {
    public UserResponseDTO(User user) {
        this(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getBirthdate(),
                user.getCountry() != null ? user.getCountry().getName() : "Não possui",
                user.getCreatedAt(),
                user.getBio(),
                user.getRoles().stream().map(Enum::name).toList()
        );
    }
}

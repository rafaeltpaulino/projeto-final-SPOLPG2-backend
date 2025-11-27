package br.com.ifsp.backend.dto.response;

import br.com.ifsp.backend.model.social.Review;

import java.time.LocalDateTime;

public record ReviewResponseDTO(
        Long id,
        String username,
        Integer rating,
        String comment,
        LocalDateTime createdAt
) {
    public ReviewResponseDTO(Review review) {
        this(
                review.getId(),
                review.getUser().getUsername(),
                review.getRating(),
                review.getComment(),
                review.getCreatedAt()
        );
    }
}

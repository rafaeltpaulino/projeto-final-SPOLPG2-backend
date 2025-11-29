package br.com.ifsp.backend.dto.response.view;

import br.com.ifsp.backend.model.social.Review;

import java.time.LocalDateTime;

public record ReviewUserResponseDTO(
        Long id,
        Long userId,          // Útil para navegação
        String username,

        // --- Novos campos de contexto da Obra ---
        Long masterId,
        String masterTitle,
        String masterCoverUrl,
        // ----------------------------------------

        Integer rating,
        String comment,
        LocalDateTime createdAt
) {
    public ReviewUserResponseDTO(Review review) {
        this(
                review.getId(),
                review.getUser().getId(),
                review.getUser().getUsername(),

                // Mapeando dados da Master Release
                review.getMaster().getId(),
                review.getMaster().getTitle(),
                review.getMaster().getCoverImageUrl(),

                review.getRating(),
                review.getComment(),
                review.getCreatedAt()
        );
    }
}
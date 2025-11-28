package br.com.ifsp.backend.dto.response.view;

import br.com.ifsp.backend.model.social.CollectionItem;

import java.time.LocalDate;

public record CollectionItemResponseDTO(
        Long id,
        String albumTitle,
        String artistName,
        String mediaCondition,
        LocalDate acquiredDate,
        String thumbUrl
) {
    public CollectionItemResponseDTO(CollectionItem item) {
        this(
                item.getId(),
                item.getRelease().getTitle(),
                item.getRelease().getMaster().getArtists().stream().findFirst().get().getName(),
                item.getMediaCondition().getLabel(),
                item.getAcquiredDate(),
                item.getRelease().getMaster().getCoverImageUrl()
        );
    }
}

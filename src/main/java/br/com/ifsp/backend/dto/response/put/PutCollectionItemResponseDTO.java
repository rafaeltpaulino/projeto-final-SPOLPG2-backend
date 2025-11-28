package br.com.ifsp.backend.dto.response.put;

import br.com.ifsp.backend.model.social.CollectionItem;

public record PutCollectionItemResponseDTO(
        Long itemId,
        Long userId,
        Long releaseId
) {
    public PutCollectionItemResponseDTO(CollectionItem item) {
        this(
                item.getId(),
                item.getUser().getId(),
                item.getRelease().getId()
        );
    }
}

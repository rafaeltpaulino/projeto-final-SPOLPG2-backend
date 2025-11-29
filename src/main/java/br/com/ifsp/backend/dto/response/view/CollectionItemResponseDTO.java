package br.com.ifsp.backend.dto.response.view;

import br.com.ifsp.backend.model.catalog.Artist;
import br.com.ifsp.backend.model.social.CollectionItem;

import java.time.LocalDate;

public record CollectionItemResponseDTO(
        Long id,              // ID do item da coleção (para editar/remover)
        Long releaseId,       // ID do disco (para clicar e ver detalhes)
        String title,         // Título da Obra/Master
        String versionTitle,  // Título da Edição (se for diferente)
        String artistName,    // Nome do artista principal
        String coverImageUrl, // Capa
        String format,        // "LP", "CD"
        String mediaCondition, // "Mint"
        LocalDate acquiredDate
) {
    public CollectionItemResponseDTO(CollectionItem item) {
        this(
                item.getId(),
                item.getRelease().getId(),
                item.getRelease().getMaster().getTitle(),
                item.getRelease().getTitle(), // Título específico da edição

                // Pega o nome do primeiro artista da lista (simplificação para grid)
                item.getRelease().getMaster().getArtists().stream()
                        .findFirst()
                        .map(Artist::getName)
                        .orElse("Unknown Artist"),

                item.getRelease().getMaster().getCoverImageUrl(),
                item.getRelease().getFormat(),
                item.getMediaCondition().getLabel(), // Usa o texto bonitinho do Enum
                item.getAcquiredDate()
        );
    }
}

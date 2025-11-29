package br.com.ifsp.backend.dto.request.patch;

import br.com.ifsp.backend.dto.request.create.ReleaseLabelRequestDTO;
import br.com.ifsp.backend.dto.request.create.TrackRequestDTO;

import java.time.LocalDate;
import java.util.List;

public record UpdateReleaseRequestDTO(
        String title,
        LocalDate releaseDate,
        String format,
        String barcode,
        Boolean isMain, // Wrapper Boolean para permitir null (não alterar)

        Long masterId,
        Long countryId,

        // Se a lista vier null, não mexemos. Se vier vazia, removemos tudo.
        List<ReleaseLabelRequestDTO> labels,
        List<TrackRequestDTO> tracks
) {}

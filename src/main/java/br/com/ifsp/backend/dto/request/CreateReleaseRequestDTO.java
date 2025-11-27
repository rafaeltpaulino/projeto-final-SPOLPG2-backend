package br.com.ifsp.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record CreateReleaseRequestDTO(
        @NotNull(message = "O ID do master é obrigatório.")
        Long masterId,
        @NotNull(message = "O ID do país é obrigatório.")
        Long countryId,
        @NotBlank(message = "O título é obrigatório")
        String title,
        @NotNull(message = "A data de lançamento é obrigatória.")
        LocalDate releaseDate,
        String format,
        String barcode,
        boolean isMain,
        @NotEmpty(message = "É necessário informar pelo menos uma gravadora.")
        List<ReleaseLabelRequestDTO> labels,
        @NotEmpty(message = "É necessário informar pelo menos uma música.")
        List<TrackRequestDTO> tracks
) {
}

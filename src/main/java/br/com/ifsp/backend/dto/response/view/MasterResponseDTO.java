package br.com.ifsp.backend.dto.response.view;

import br.com.ifsp.backend.model.catalog.Artist;
import br.com.ifsp.backend.model.catalog.Genre;
import br.com.ifsp.backend.model.catalog.Master;
import br.com.ifsp.backend.model.catalog.Release;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public record MasterResponseDTO(
        Long id,
        String title,
        Integer releaseYear,
        String coverImageUrl,
        Double averageRating,
        String description,
        List<String> artists,       // Convertido de Set<Artist>
        List<String> genres,        // Convertido de Set<Genre>
        List<ReleaseSimpleDTO> releases, // DTO Simplificado para evitar Loop
        List<TrackSimpleDTO> tracks // As faixas da versão principal
) {

    public MasterResponseDTO(Master master) {
        this(
                master.getId(),
                master.getTitle(),
                master.getReleaseYear(),
                master.getCoverImageUrl(),
                master.getAverageRating(),
                master.getDescription(),

                // 1. Converte Artistas para Lista de Nomes
                master.getArtists().stream()
                        .map(Artist::getName)
                        .toList(),

                // 2. Converte Gêneros para Lista de Nomes
                master.getGenres().stream()
                        .map(Genre::getName)
                        .toList(),

                // 3. Converte Releases para DTO Simplificado
                master.getReleases() != null ?
                        master.getReleases().stream()
                                .map(ReleaseSimpleDTO::new) // Usa o construtor do DTO abaixo
                                .toList() : Collections.emptyList(),

                // 4. Lógica para pegar as Faixas (Tracks) da Versão Principal
                extractMainTracks(master)
        );
    }

    // Método auxiliar para achar as músicas da versão principal
    private static List<TrackSimpleDTO> extractMainTracks(Master master) {
        if (master.getReleases() == null || master.getReleases().isEmpty()) {
            return Collections.emptyList();
        }

        // Tenta achar a marcada como "Main". Se não tiver, pega a primeira da lista.
        Optional<Release> mainRelease = master.getReleases().stream()
                .filter(Release::isMain)
                .findFirst();

        Release targetRelease = mainRelease.orElse(master.getReleases().get(0));

        // Retorna as faixas ordenadas pela posição (A1, A2...)
        return targetRelease.getTracks().stream()
                .sorted(Comparator.comparing(t -> t.getPosition()))
                .map(t -> new TrackSimpleDTO(t.getPosition(), t.getTitle(), t.getDurationSeconds()))
                .toList();
    }

    // --- DTOs Internos (Para não precisar criar arquivos separados) ---

    public record ReleaseSimpleDTO(Long id, String title, String format, String country) {
        public ReleaseSimpleDTO(Release release) {
            this(
                    release.getId(),
                    release.getTitle(),
                    release.getFormat(),
                    release.getCountry() != null ? release.getCountry().getName() : null
            );
        }
    }

    public record TrackSimpleDTO(String position, String title, Integer duration) {}
}
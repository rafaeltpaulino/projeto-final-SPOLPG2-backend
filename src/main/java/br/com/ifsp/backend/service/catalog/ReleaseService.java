package br.com.ifsp.backend.service.catalog;

import br.com.ifsp.backend.builder.ReleaseBuilder;
import br.com.ifsp.backend.dto.request.create.CreateReleaseRequestDTO;
import br.com.ifsp.backend.dto.request.create.ReleaseLabelRequestDTO;
import br.com.ifsp.backend.dto.request.patch.UpdateReleaseRequestDTO;
import br.com.ifsp.backend.exceptions.ResourceNotFoundException;
import br.com.ifsp.backend.model.Country;
import br.com.ifsp.backend.model.catalog.*;
import br.com.ifsp.backend.repository.catalog.ReleaseRepository;
import br.com.ifsp.backend.service.CountryService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReleaseService {

    private final ReleaseRepository releaseRepository;
    private final MasterService masterService;
    private final LabelService labelService;
    private final CountryService countryService;

    public ReleaseService(ReleaseRepository releaseRepository, MasterService masterService, LabelService labelService, CountryService countryService) {
        this.releaseRepository = releaseRepository;
        this.masterService = masterService;
        this.labelService = labelService;
        this.countryService = countryService;
    }

    @Transactional
    public Release createRelease(CreateReleaseRequestDTO data) {
        Master master = masterService.findById(data.masterId());

        Country country = countryService.findById(data.countryId());

        ReleaseBuilder builder = ReleaseBuilder.create()
                .withTitle(data.title())
                .ofMaster(master)
                .fromCountry(country)
                .releasedIn(data.releaseDate())
                .withFormat(data.format())
                .asMainRelease(data.isMain());

        for (ReleaseLabelRequestDTO labelDto : data.labels()) {
            Label label = labelService.findById(labelDto.labelId());

            builder.addLabel(label, labelDto.catalogNumber(), labelDto.role());
        }

        Release release = builder.build();

        List<Track> tracks = data.tracks().stream().map(trackDto -> {
            Track track = new Track();
            track.setTitle(trackDto.title());
            track.setPosition(trackDto.position());
            track.setDurationSeconds(trackDto.durationSeconds());
            track.setRelease(release);
            return track;
        }).toList();

        release.setTracks(tracks);

        releaseRepository.save(release);

        return release;
    }

    public List<Release> listAll() {
        return releaseRepository.findAll();
    }

    public Page<Release> findAll(String title, Pageable pageable) {
        if (title != null && !title.isBlank()) {
            return releaseRepository.findByTitleContainingIgnoreCase(title, pageable);
        }
        return releaseRepository.findAll(pageable);
    }

    public Release findById(Long id) {
        return releaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum release foi encontado com o ID: " + id));
    }

    @Transactional
    public Release update(Long id, UpdateReleaseRequestDTO data) {
        Release release = findById(id);

        // 1. Atualizar Campos Simples (se não forem nulos)
        if (data.title() != null) release.setTitle(data.title());
        if (data.releaseDate() != null) release.setReleaseDate(data.releaseDate());
        if (data.format() != null) release.setFormat(data.format());
        if (data.barcode() != null) release.setBarcode(data.barcode());
        if (data.isMain() != null) release.setMain(data.isMain());

        // 2. Atualizar Associações Simples (Master e Country)
        if (data.masterId() != null) {
            Master master = masterService.findById(data.masterId());
            release.setMaster(master);
        }
        if (data.countryId() != null) {
            Country country = countryService.findById(data.countryId());
            release.setCountry(country);
        }

        // 3. Atualizar Labels (Estratégia: Limpar e Reconstruir)
        if (data.labels() != null) {
            // Remove as antigas do banco graças ao orphanRemoval=true
            release.getLabels().clear();

            for (var labelDto : data.labels()) {
                Label label = labelService.findById(labelDto.labelId());

                ReleaseLabel releaseLabel = new ReleaseLabel();
                releaseLabel.setRelease(release);
                releaseLabel.setLabel(label);
                releaseLabel.setCatalogNumber(labelDto.catalogNumber());
                releaseLabel.setRole(labelDto.role());

                // Configurar chave composta
                ReleaseLabelId linkId = new ReleaseLabelId();
                linkId.setReleaseId(release.getId());
                linkId.setLabelId(label.getId());
                releaseLabel.setId(linkId);

                release.getLabels().add(releaseLabel);
            }
        }

        // 4. Atualizar Faixas (Estratégia: Limpar e Reconstruir)
        if (data.tracks() != null) {
            release.getTracks().clear(); // Apaga as faixas antigas

            for (var trackDto : data.tracks()) {
                Track track = new Track();
                track.setTitle(trackDto.title());
                track.setPosition(trackDto.position());
                track.setDurationSeconds(trackDto.durationSeconds());
                track.setRelease(release); // Vincula ao pai

                release.getTracks().add(track);
            }
        }

        return releaseRepository.save(release);
    }
}

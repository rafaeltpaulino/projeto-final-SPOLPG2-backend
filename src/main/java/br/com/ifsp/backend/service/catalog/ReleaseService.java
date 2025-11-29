package br.com.ifsp.backend.service.catalog;

import br.com.ifsp.backend.builder.ReleaseBuilder;
import br.com.ifsp.backend.dto.request.create.CreateReleaseRequestDTO;
import br.com.ifsp.backend.dto.request.create.ReleaseLabelRequestDTO;
import br.com.ifsp.backend.exceptions.ResourceNotFoundException;
import br.com.ifsp.backend.model.Country;
import br.com.ifsp.backend.model.catalog.Label;
import br.com.ifsp.backend.model.catalog.Master;
import br.com.ifsp.backend.model.catalog.Release;
import br.com.ifsp.backend.model.catalog.Track;
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
}

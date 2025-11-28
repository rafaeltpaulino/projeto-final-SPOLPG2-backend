package br.com.ifsp.backend.service.catalog;

import br.com.ifsp.backend.dto.request.create.CreateArtistRequestDTO;
import br.com.ifsp.backend.dto.request.patch.PatchArtistRequestDTO;
import br.com.ifsp.backend.exceptions.ResourceNotFoundException;
import br.com.ifsp.backend.model.catalog.Artist;
import br.com.ifsp.backend.model.Country;
import br.com.ifsp.backend.repository.catalog.ArtistRepository;
import br.com.ifsp.backend.repository.CountryRepository;
import br.com.ifsp.backend.service.CountryService;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final CountryRepository countryRepository;
    private final CountryService countryService;

    public ArtistService(CountryRepository countryRepository, ArtistRepository artistRepository, CountryService countryService){
        this.countryRepository = countryRepository;
        this.artistRepository = artistRepository;
        this.countryService = countryService;
    }

    public Artist insertArtist(CreateArtistRequestDTO data) {
        Country country = countryRepository.findById(data.countryId())
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum país encontrado com o ID: " + data.countryId()));

        Artist newArtist = new Artist();

        newArtist.setName(data.name());
        newArtist.setDescription(data.description());
        newArtist.setImageUrl(data.imageUrl());
        newArtist.setStartDate(data.startDate());
        newArtist.setEndDate(data.endDate());
        newArtist.setCountry(country);

        artistRepository.save(newArtist);

        return newArtist;
    }

    public List<Artist> listAll() {
        return artistRepository.findAll();
    }

    public Artist findById(Long id) {
        return artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum artista encontrado com o ID: " + id));
    }

    public List<Artist> findAllById(Set<Long> artistsId) {
        return artistRepository.findAllById(artistsId);
    }

    @Transactional
    public Artist update(Long id, PatchArtistRequestDTO data) {
        Artist artist = findById(id);

        if (data.name() != null) artist.setName(data.name());
        if (data.description() != null) artist.setDescription(data.description());
        if (data.imageUrl() != null) artist.setImageUrl(data.imageUrl());
        if (data.startDate() != null) artist.setStartDate(data.startDate());
        if (data.endDate() != null) artist.setEndDate(data.endDate());
        if (data.countryId() != null) {
            Country country = countryService.findById(data.countryId());
            artist.setCountry(country);
        }
        artistRepository.save(artist);

        return artist;
    }

    @Transactional
    public void delete(Long id) {
        if (!artistRepository.existsById(id)) {
            throw new ResourceNotFoundException("Nenhum artista encontrado com ID: " + id);
        }

        try {
            artistRepository.deleteById(id);
            artistRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Não é possível excluir este artista pois ele possui Obras (Masters) associadas.");
        }
    }
}

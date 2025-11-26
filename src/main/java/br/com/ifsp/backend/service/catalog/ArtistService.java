package br.com.ifsp.backend.service.catalog;

import br.com.ifsp.backend.dto.request.CreateArtistRequestDTO;
import br.com.ifsp.backend.exceptions.ResourceNotFoundException;
import br.com.ifsp.backend.model.catalog.Artist;
import br.com.ifsp.backend.model.Country;
import br.com.ifsp.backend.repository.catalog.ArtistRepository;
import br.com.ifsp.backend.repository.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final CountryRepository countryRepository;

    public ArtistService(CountryRepository countryRepository, ArtistRepository artistRepository){
        this.countryRepository = countryRepository;
        this.artistRepository = artistRepository;
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
}

package br.com.ifsp.backend.service.catalog;

import br.com.ifsp.backend.dto.request.CreateMasterRequestDTO;
import br.com.ifsp.backend.exceptions.ResourceNotFoundException;
import br.com.ifsp.backend.model.catalog.Artist;
import br.com.ifsp.backend.model.catalog.Genre;
import br.com.ifsp.backend.model.catalog.Master;
import br.com.ifsp.backend.repository.catalog.MasterRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class MasterService {

    private final MasterRepository masterRepository;
    private final ArtistService artistService;
    private final GenreService genreService;

    public MasterService(MasterRepository masterRepository, ArtistService artistService, GenreService genreService) {
        this.masterRepository = masterRepository;
        this.artistService = artistService;
        this.genreService = genreService;
    }

    @Transactional
    public Master createMaster(CreateMasterRequestDTO data) {
        Set<Artist> artists = new LinkedHashSet<>(artistService.findAllById(data.artistsId()));
        if(artists.size() != data.artistsId().size()) {
            throw new ResourceNotFoundException("Um ou mais artistas informados não foram encontrados.");
        }

        Set<Genre> genres = new LinkedHashSet<>(genreService.findAllById(data.genresId()));
        if(genres.size() != data.genresId().size()) {
            throw new ResourceNotFoundException("Um ou mais gêneros musicais informados não foram encontrados.");
        }

       var newMaster = new Master();

        newMaster.setTitle(data.title());
        newMaster.setReleaseYear(data.releaseYear());
        newMaster.setCoverImageUrl(data.coverImageUrl());
        newMaster.setDescription(data.description());
        newMaster.setArtists(artists);
        newMaster.setGenres(genres);

        masterRepository.save(newMaster);

        return newMaster;
    }

    public List<Master> listAll() { return masterRepository.findAll(); }

    public Master findById(Long id) {
        return masterRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nenhuma master encontrad com o ID: " + id));
    }
}

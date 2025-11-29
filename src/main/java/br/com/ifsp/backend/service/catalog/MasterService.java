package br.com.ifsp.backend.service.catalog;

import br.com.ifsp.backend.dto.request.create.CreateMasterRequestDTO;
import br.com.ifsp.backend.dto.request.patch.UpdateMasterRequestDTO;
import br.com.ifsp.backend.exceptions.ResourceNotFoundException;
import br.com.ifsp.backend.model.catalog.Artist;
import br.com.ifsp.backend.model.catalog.Genre;
import br.com.ifsp.backend.model.catalog.Master;
import br.com.ifsp.backend.repository.catalog.MasterRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
                .orElseThrow(() -> new ResourceNotFoundException("Nenhuma master encontrado com o ID: " + id));
    }

    public Page<Master> findAll(String title, Pageable pageable){
        if (title != null && !title.isBlank()) {
            return masterRepository.findByTitleContainingIgnoreCase(title, pageable);
        }
        return masterRepository.findAll(pageable);
    }

    @Transactional
    public Master update(Long id, UpdateMasterRequestDTO data) {
        Master master = findById(id); // Usa seu método que já valida existência

        // 1. Atualiza campos simples (se não forem nulos)
        if (data.title() != null) master.setTitle(data.title());
        if (data.releaseYear() != null) master.setReleaseYear(data.releaseYear());
        if (data.coverImageUrl() != null) master.setCoverImageUrl(data.coverImageUrl());
        if (data.description() != null) master.setDescription(data.description());

        // 2. Atualiza Artistas (Se a lista foi enviada)
        if (data.artistsId() != null) {
            // Busca os novos artistas
            Set<Artist> newArtists = new LinkedHashSet<>(artistService.findAllById(data.artistsId()));

            // Validação de integridade (opcional, mas recomendada)
            if (newArtists.size() != data.artistsId().size()) {
                throw new ResourceNotFoundException("Um ou mais IDs de artistas não foram encontrados.");
            }

            // Substitui a lista antiga pela nova
            master.setArtists(newArtists);
        }

        // 3. Atualiza Gêneros (Se a lista foi enviada)
        if (data.genresId() != null) {
            Set<Genre> newGenres = new LinkedHashSet<>(genreService.findAllById(data.genresId()));

            if (newGenres.size() != data.genresId().size()) {
                throw new ResourceNotFoundException("Um ou mais IDs de gêneros não foram encontrados.");
            }

            master.setGenres(newGenres);
        }

        return masterRepository.save(master);
    }

    @Transactional
    public void delete(Long id) {
        // 1. Busca a Master (precisamos carregar os releases para checar)
        Master master = findById(id);

        // 2. REGRA DE NEGÓCIO: Não pode apagar se tiver edições físicas
        // (Isso evita apagar o catálogo inteiro sem querer)
        if (!master.getReleases().isEmpty()) {
            throw new IllegalArgumentException("Não é possível excluir esta Obra pois ela possui Edições (Releases) cadastradas. Exclua as edições primeiro.");
        }

        // 3. Tenta deletar
        try {
            masterRepository.delete(master);
            masterRepository.flush(); // Força o SQL agora para pegar o erro de FK
        } catch (DataIntegrityViolationException e) {
            // Esse erro acontece se tiver Reviews associadas (já que Master não tem lista de reviews mapeada com Cascade)
            throw new IllegalArgumentException("Não é possível excluir esta Obra pois ela possui Avaliações (Reviews) de usuários.");
        }
    }
}

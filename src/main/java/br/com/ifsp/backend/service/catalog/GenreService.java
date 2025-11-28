package br.com.ifsp.backend.service.catalog;

import br.com.ifsp.backend.dto.request.create.CreateGenreRequestDTO;
import br.com.ifsp.backend.exceptions.ResourceNotFoundException;
import br.com.ifsp.backend.model.catalog.Genre;
import br.com.ifsp.backend.repository.catalog.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Genre insertGenre(CreateGenreRequestDTO data) {
        Genre newGenre = new Genre();
        newGenre.setName(data.name());
        genreRepository.save(newGenre);

        return newGenre;
    }

    public List<Genre> listAll() {
        return genreRepository.findAll();
    }

    public Genre findById(Long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum gênero musical encontrado com o ID: " + id));
    }

    public List<Genre> findAllById(Set<Long> genresId) {
        return genreRepository.findAllById(genresId);
    }
}

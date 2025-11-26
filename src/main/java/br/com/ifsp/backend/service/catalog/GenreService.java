package br.com.ifsp.backend.service.catalog;

import br.com.ifsp.backend.dto.request.InsertGenreRequestDTO;
import br.com.ifsp.backend.exceptions.ResourceNotFoundException;
import br.com.ifsp.backend.model.catalog.Genre;
import br.com.ifsp.backend.repository.catalog.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public Genre insertGenre(InsertGenreRequestDTO data) {
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
}

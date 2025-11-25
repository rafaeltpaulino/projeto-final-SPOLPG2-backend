package br.com.ifsp.backend.repository;

import br.com.ifsp.backend.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}

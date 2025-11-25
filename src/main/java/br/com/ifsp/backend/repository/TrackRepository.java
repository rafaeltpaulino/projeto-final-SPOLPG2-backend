package br.com.ifsp.backend.repository;

import br.com.ifsp.backend.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackRepository extends JpaRepository<Track, Long> {
}

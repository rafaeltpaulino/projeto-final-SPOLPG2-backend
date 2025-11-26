package br.com.ifsp.backend.repository.catalog;

import br.com.ifsp.backend.model.catalog.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {
}

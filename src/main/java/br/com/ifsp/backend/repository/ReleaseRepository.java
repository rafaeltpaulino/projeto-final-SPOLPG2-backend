package br.com.ifsp.backend.repository;

import br.com.ifsp.backend.model.Release;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReleaseRepository extends JpaRepository<Release, Long> {
}

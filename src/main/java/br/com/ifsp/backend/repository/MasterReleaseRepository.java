package br.com.ifsp.backend.repository;

import br.com.ifsp.backend.model.MasterRelease;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MasterReleaseRepository extends JpaRepository<MasterRelease, Long> {
}

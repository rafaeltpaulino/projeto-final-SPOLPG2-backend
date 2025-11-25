package br.com.ifsp.backend.repository;

import br.com.ifsp.backend.model.ReleaseLabel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReleaseLabelRepository extends JpaRepository<ReleaseLabel, Long> {
}

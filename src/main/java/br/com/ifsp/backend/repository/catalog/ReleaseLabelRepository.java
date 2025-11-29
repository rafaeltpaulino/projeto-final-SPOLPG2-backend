package br.com.ifsp.backend.repository.catalog;

import br.com.ifsp.backend.model.catalog.ReleaseLabel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReleaseLabelRepository extends JpaRepository<ReleaseLabel, Long> {

    boolean existsByLabelId(Long labelId);
}

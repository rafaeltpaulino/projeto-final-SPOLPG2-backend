package br.com.ifsp.backend.repository.catalog;

import br.com.ifsp.backend.model.catalog.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long> {
}

package br.com.ifsp.backend.repository;

import br.com.ifsp.backend.model.Label;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabelRepository extends JpaRepository<Label, Long> {
}

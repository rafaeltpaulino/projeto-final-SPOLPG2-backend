package br.com.ifsp.backend.repository.catalog;

import br.com.ifsp.backend.model.catalog.Artist;
import br.com.ifsp.backend.model.catalog.Master;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterRepository extends JpaRepository<Master, Long> {
    Page<Master> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}

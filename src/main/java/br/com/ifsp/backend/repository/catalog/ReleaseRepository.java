package br.com.ifsp.backend.repository.catalog;

import br.com.ifsp.backend.model.catalog.Release;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReleaseRepository extends JpaRepository<Release, Long> {
    Page<Release> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}

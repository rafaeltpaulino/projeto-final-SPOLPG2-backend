package br.com.ifsp.backend.repository.social;

import br.com.ifsp.backend.model.social.CollectionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionItemRepository extends JpaRepository<CollectionItem, Long> {
    List<CollectionItem> findByUserId(Long userId);
}

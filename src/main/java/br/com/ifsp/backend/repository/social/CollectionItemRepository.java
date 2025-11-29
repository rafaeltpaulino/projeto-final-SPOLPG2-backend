package br.com.ifsp.backend.repository.social;

import br.com.ifsp.backend.model.social.CollectionItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionItemRepository extends JpaRepository<CollectionItem, Long> {
    List<CollectionItem> findByUserId(Long userId);

    @Query(value = "SELECT ci FROM CollectionItem ci " +
            "JOIN FETCH ci.release r " +
            "JOIN FETCH r.master m " +
            "WHERE ci.user.id = :userId",
            countQuery = "SELECT count(ci) FROM CollectionItem ci WHERE ci.user.id = :userId")
    Page<CollectionItem> findAllByUserIdWithRelations(@Param("userId") Long userId, Pageable pageable);

    boolean existsByReleaseId(Long releaseId);
}

package br.com.ifsp.backend.repository.social;

import br.com.ifsp.backend.model.social.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.master.id = :masterId")
    Double getAvgRatingById(@Param("masterId") Long masterId);

    boolean existsByUserIdAndMasterId(Long userId, Long masterId);

    List<Review> findByMasterId(Long masterId);

    @Query("SELECT r FROM Review r " +
                  "JOIN FETCH r.master m " + // Carrega a Obra junto
                  "WHERE r.user.id = :userId")
    Page<Review> findAllByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query(value = "SELECT r FROM Review r JOIN FETCH r.user WHERE r.master.id = :masterId",
            countQuery = "SELECT count(r) FROM Review r WHERE r.master.id = :masterId")
    Page<Review> findAllByMasterId(@Param("masterId") Long masterId, Pageable pageable);
}

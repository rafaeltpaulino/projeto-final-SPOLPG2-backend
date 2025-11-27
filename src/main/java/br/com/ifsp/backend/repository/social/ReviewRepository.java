package br.com.ifsp.backend.repository.social;

import br.com.ifsp.backend.model.social.Review;
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
}

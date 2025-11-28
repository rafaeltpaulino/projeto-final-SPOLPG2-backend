package br.com.ifsp.backend.service.social;

import br.com.ifsp.backend.dto.request.create.CreateReviewRequestDTO;
import br.com.ifsp.backend.model.catalog.Master;
import br.com.ifsp.backend.model.social.Review;
import br.com.ifsp.backend.model.user.User;
import br.com.ifsp.backend.repository.social.ReviewRepository;
import br.com.ifsp.backend.service.catalog.MasterService;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    private final ApplicationEventPublisher eventPublisher;
    private final ReviewRepository reviewRepository;
    private final MasterService masterService;

    public ReviewService(ApplicationEventPublisher eventPublisher, ReviewRepository reviewRepository, MasterService masterService){
    	this.eventPublisher = eventPublisher;
        this.reviewRepository = reviewRepository;
        this.masterService = masterService;
    }

    @Transactional
    public Review saveReview(CreateReviewRequestDTO dto, User currentUser) {
        Master master = masterService.findById(dto.masterId());

        if (reviewRepository.existsByUserIdAndMasterId(currentUser.getId(), master.getId())) {
            throw new IllegalArgumentException("Você já avaliou este álbum. Edite sua avaliação existente.");
        }

        Review review = new Review();
        review.setUser(currentUser);
        review.setMaster(master);
        review.setRating(dto.rating());
        review.setComment(dto.comment());

        reviewRepository.save(review);

        // DISPARA O EVENTO: "Alguém avaliou esse disco!"
        eventPublisher.publishEvent(new ReviewCreatedEvent(master.getId()));

        return review;
    }

    public List<Review> findByMasterId(Long id) {
        return reviewRepository.findByMasterId(id);
    }
}

package br.com.ifsp.backend.service.social;

import br.com.ifsp.backend.dto.request.create.CreateReviewRequestDTO;
import br.com.ifsp.backend.dto.request.patch.UpdateReviewRequestDTO;
import br.com.ifsp.backend.exceptions.ResourceNotFoundException;
import br.com.ifsp.backend.model.catalog.Master;
import br.com.ifsp.backend.model.social.Review;
import br.com.ifsp.backend.model.user.User;
import br.com.ifsp.backend.repository.social.ReviewRepository;
import br.com.ifsp.backend.service.catalog.MasterService;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Transactional
    public Review update(Long reviewId, Long userId, UpdateReviewRequestDTO data) {
        // 1. Busca a review
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Avaliação não encontrada com ID: " + reviewId));

        // 2. SEGURANÇA: Verifica se é o dono
        if (!review.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Você não tem permissão para editar esta avaliação.");
        }

        // 3. Atualiza campos (Se não forem nulos)
        boolean ratingChanged = false;

        if (data.rating() != null) {
            review.setRating(data.rating());
            ratingChanged = true;
        }

        if (data.comment() != null) {
            review.setComment(data.comment());
        }

        Review savedReview = reviewRepository.save(review);

        // 4. OBSERVER: Se a nota mudou, avisa o sistema para recalcular a média do álbum!
        if (ratingChanged) {
            // Reutilizamos o evento existente, pois a lógica de recalcular é a mesma
            eventPublisher.publishEvent(new ReviewCreatedEvent(review.getMaster().getId()));
        }

        return savedReview;
    }

    @Transactional()
    public Page<Review> findByMasterId(Long masterId, int page, int size) {
        // Ordena por data de criação (do mais novo para o mais velho)
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        return reviewRepository.findAllByMasterId(masterId, pageable);
    }

    @Transactional()
    public Page<Review> listByUser(Long userId, int page, int size) {
        // Ordena pela data de criação (mais recentes primeiro)
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        return reviewRepository.findAllByUserId(userId, pageable);
    }

    @Transactional
    public void delete(Long reviewId, Long userId) {
        // 1. Busca a review
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Avaliação não encontrada com ID: " + reviewId));

        // 2. SEGURANÇA: Verifica se é o dono
        if (!review.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Você não tem permissão para excluir esta avaliação.");
        }

        // Guardamos o ID da Master antes de deletar a review para poder recalcular a média depois
        Long masterId = review.getMaster().getId();

        // 3. Deleta
        reviewRepository.delete(review);

        // 4. OBSERVER: Dispara evento para recalcular a média do álbum
        // (A média vai mudar porque removemos uma nota)
        eventPublisher.publishEvent(new ReviewCreatedEvent(masterId));
    }
}

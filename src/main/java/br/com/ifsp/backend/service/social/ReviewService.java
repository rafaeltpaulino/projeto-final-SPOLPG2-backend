@Service
public class ReviewService {

    private final ApplicationEventPublisher eventPublisher;
    
    public ReviewService(ApplicationEventPublisher eventPublisher){
    	this.eventPublisher = eventPublisher;
    }

    public void saveReview(ReviewDTO dto) {
        // ... salva a review no banco ...
        repository.save(review);

        // DISPARA O EVENTO: "Alguém avaliou esse disco!"
        eventPublisher.publishEvent(new ReviewCreatedEvent(dto.masterReleaseId()));
    }
}

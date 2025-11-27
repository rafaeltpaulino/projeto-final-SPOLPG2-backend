@Component
public class MasterReleaseRatingUpdater {

    private final MasterReleaseRepository masterRepository;
    private final ReviewRepository reviewRepository;
    
    public MasterReleaseRatingUpdater(MasterReleaseRepository masterRepository, ReviewRepository reviewRepository){
    	this.masterRepository = masterRepository;
    	this.reviewRepository = reviewRepository;
    }
 
    // O Spring fica ouvindo. Quando o evento ocorrer, ele roda isso aqui.
    @EventListener
    @Async // Pode até rodar em segundo plano para não travar o usuário!
    public void handleReviewCreated(ReviewCreatedEvent event) {
        // 1. Busca todas as notas desse álbum
        Double novaMedia = reviewRepository.calculateAverage(event.getMasterReleaseId());
        
        // 2. Atualiza a entidade MasterRelease
        MasterRelease master = masterRepository.findById(event.getMasterReleaseId()).get();
        master.setAverageRating(novaMedia);
        masterRepository.save(master);
        
        System.out.println("Média recalculada via Observer!");
    }
}

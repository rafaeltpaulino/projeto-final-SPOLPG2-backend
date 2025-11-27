package br.com.ifsp.backend.service.social;

import br.com.ifsp.backend.model.catalog.Master;
import br.com.ifsp.backend.repository.catalog.MasterRepository;
import br.com.ifsp.backend.repository.social.ReviewRepository;
import br.com.ifsp.backend.service.catalog.MasterService;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class MasterReleaseRatingUpdater {

    private final MasterRepository masterRepository;
    private final ReviewRepository reviewRepository;
    private final MasterService masterService;

    public MasterReleaseRatingUpdater(MasterRepository masterRepository, ReviewRepository reviewRepository, MasterService masterService){
    	this.masterRepository = masterRepository;
    	this.reviewRepository = reviewRepository;
        this.masterService = masterService;
    }
 
    // O Spring fica ouvindo. Quando o evento ocorrer, ele roda isso aqui.
    @EventListener
    @Async // Pode até rodar em segundo plano para não travar o usuário!
    public void handleReviewCreated(ReviewCreatedEvent event) {
        // 1. Busca todas as notas desse álbum
        Double novaMedia = reviewRepository.getAvgRatingById(event.masterId());
        
        // 2. Atualiza a entidade MasterRelease
        Master master = masterService.findById(event.masterId());
        master.setAverageRating(novaMedia);
        masterRepository.save(master);
        
        System.out.println("Média recalculada via Observer!");
    }
}

package br.com.ifsp.backend.service.social;

import br.com.ifsp.backend.dto.request.InsertIntoCollectionRequestDTO;
import br.com.ifsp.backend.model.social.CollectionItem;
import br.com.ifsp.backend.repository.social.CollectionItemRepository;
import br.com.ifsp.backend.service.catalog.ReleaseService;
import br.com.ifsp.backend.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

// 4. Uso no CollectionService (Contexto)
@Service
    public class CollectionItemService {

        private final CollectionItemRepository collectionItemRepository;
        private final Map<String, CollectionSortStrategy> sortStrategies;
    private final UserService userService;
    private final ReleaseService releaseService;

    public CollectionItemService(CollectionItemRepository collectionItemRepository, Map<String, CollectionSortStrategy> sortStrategies, UserService userService, ReleaseService releaseService) {
            this.collectionItemRepository = collectionItemRepository;
            this.sortStrategies = sortStrategies;
            this.userService = userService;
            this.releaseService = releaseService;
    }

        public List<CollectionItem> getCollection(Long userId, String sortType) {

            List<CollectionItem> items = collectionItemRepository.findByUserId(userId);

            if (items.isEmpty()) {
                return items;
            }

            String strategyKey = (sortType == null || !sortStrategies.containsKey(sortType))
                    ? "SortByAcquiredDate"
                    : sortType;

            CollectionSortStrategy strategy = sortStrategies.get(strategyKey);

            return strategy.sort(items);
            }

            public CollectionItem insertIntoCollection(InsertIntoCollectionRequestDTO data) {
                var newcollectionItem = new CollectionItem();
                var user = userService.findById(data.userId());
                var release = releaseService.findById(data.releaseId());

                newcollectionItem.setUser(user);
                newcollectionItem.setRelease(release);
                newcollectionItem.setAcquiredDate(data.acquiredDate());
                newcollectionItem.setMediaCondition(data.mediaCondition());
                newcollectionItem.setSleeveCondition(data.sleeveCondition());
                newcollectionItem.setPrivateNotes(data.privateNotes());

                collectionItemRepository.save(newcollectionItem);

                return newcollectionItem;
            }
    }
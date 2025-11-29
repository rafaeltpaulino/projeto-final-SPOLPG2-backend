package br.com.ifsp.backend.service.social;

import br.com.ifsp.backend.dto.request.patch.UpdateCollectionItemRequestDTO;
import br.com.ifsp.backend.dto.request.put.InsertIntoCollectionRequestDTO;
import br.com.ifsp.backend.exceptions.ResourceNotFoundException;
import br.com.ifsp.backend.model.social.CollectionItem;
import br.com.ifsp.backend.model.social.ConditionEnum;
import br.com.ifsp.backend.repository.social.CollectionItemRepository;
import br.com.ifsp.backend.service.catalog.ReleaseService;
import br.com.ifsp.backend.service.user.UserService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

        public Page<CollectionItem> getMyCollection(Long userId, String sortType, int page, int size) {

            // 1. Define a estratégia de ordenação (Default: Data de Aquisição)
            String strategyKey = (sortType == null || !sortStrategies.containsKey(sortType))
                    ? "SortByAcquiredDate"
                    : sortType;

            Sort sort = sortStrategies.get(strategyKey).getSort();

            // 2. Cria o objeto Pageable combinando Paginação + Ordenação
            Pageable pageRequest = PageRequest.of(page, size, sort);

            // 3. Busca no banco já paginado
            return collectionItemRepository.findAllByUserIdWithRelations(userId, pageRequest);
        }

            public CollectionItem insertIntoCollection(InsertIntoCollectionRequestDTO data, Long userId) {
                var newcollectionItem = new CollectionItem();
                var user = userService.findById(userId);
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

            public CollectionItem findById(Long id) {
                return collectionItemRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Não existe nenhum item na coleção com o ID: " + id));
            }

        @Transactional
        public void deleteItem(Long itemId, Long userId) {
            // 1. Busca o item no banco
            CollectionItem item = findById(itemId);

            // 2. VERIFICAÇÃO DE SEGURANÇA: O item pertence ao usuário logado?
            if (!item.getUser().getId().equals(userId)) {
                // Retorna erro se tentar apagar o disco do vizinho
                throw new IllegalArgumentException("Você não tem permissão para remover este item.");
            }
            // 3. Deleta
            collectionItemRepository.delete(item);
        }

    @Transactional
    public CollectionItem updateItem(Long itemId, Long userId, UpdateCollectionItemRequestDTO data) {
        // 1. Busca o item
        CollectionItem item = collectionItemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado na coleção: " + itemId));

        // 2. SEGURANÇA: Verifica se é o dono
        if (!item.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Você não tem permissão para editar este item.");
        }

        // 3. Atualiza campos (Se não forem nulos)
        if (data.acquiredDate() != null) {
            item.setAcquiredDate(data.acquiredDate());
        }

        if (data.privateNotes() != null) {
            item.setPrivateNotes(data.privateNotes());
        }

        // 4. Lógica de Enum (Converte String -> Enum com segurança)
        if (data.mediaCondition() != null) {
            try {
                item.setMediaCondition(ConditionEnum.valueOf(data.mediaCondition().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Condição de Mídia inválida: " + data.mediaCondition());
            }
        }

        if (data.sleeveCondition() != null) {
            try {
                item.setSleeveCondition(ConditionEnum.valueOf(data.sleeveCondition().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Condição de Capa inválida: " + data.sleeveCondition());
            }
        }

        return collectionItemRepository.save(item);
    }
    }
package br.com.ifsp.backend.service.social;

import br.com.ifsp.backend.model.social.CollectionItem;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

// 3. Implementação B (Por Data de Aquisição)
@Component("SortByAcquiredDate")
    public class SortByAcquiredDate implements CollectionSortStrategy {

        @Override
        public List<CollectionItem> sort(List<CollectionItem> items) {
            return items.stream()
            .sorted(Comparator.comparing(CollectionItem::getAcquiredDate, Comparator.nullsLast(Comparator.naturalOrder())).reversed())
            .toList();
        }
    }
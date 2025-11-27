package br.com.ifsp.backend.service.social;

import br.com.ifsp.backend.model.social.CollectionItem;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

// 2. Implementação A (Por Ano de Lançamento)
@Component("SortByYear")
    public class SortByReleaseYear implements CollectionSortStrategy {
        @Override
        public List<CollectionItem> sort(List<CollectionItem> items) {
                return items.stream()
                .sorted(Comparator.comparing(item -> item.getRelease().getReleaseDate()))
                .toList();
        }
    }
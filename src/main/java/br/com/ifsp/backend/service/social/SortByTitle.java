package br.com.ifsp.backend.service.social;

import br.com.ifsp.backend.model.social.CollectionItem;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component("SortByTitle")
public class SortByTitle implements CollectionSortStrategy {

    @Override
    public List<CollectionItem> sort(List<CollectionItem> items) {
        return items.stream()
                .sorted(Comparator.comparing(item -> item.getRelease().getTitle(), String.CASE_INSENSITIVE_ORDER))
                .toList();
    }
}

package br.com.ifsp.backend.service.social;

import br.com.ifsp.backend.model.social.CollectionItem;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component("SortByTitle")
public class SortByTitle implements CollectionSortStrategy {
    @Override
    public Sort getSort() {
        return Sort.by(Sort.Direction.ASC, "release.master.title");
    }
}

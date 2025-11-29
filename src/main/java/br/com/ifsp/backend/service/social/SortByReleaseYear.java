package br.com.ifsp.backend.service.social;

import br.com.ifsp.backend.model.social.CollectionItem;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

// 2. Implementação A (Por Ano de Lançamento)
@Component("SortByYear")
public class SortByReleaseYear implements CollectionSortStrategy {
    @Override
    public Sort getSort() {
        return Sort.by(Sort.Direction.ASC, "release.master.releaseYear");
    }
}
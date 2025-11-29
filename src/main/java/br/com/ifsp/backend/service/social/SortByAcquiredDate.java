package br.com.ifsp.backend.service.social;

import br.com.ifsp.backend.model.social.CollectionItem;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

// 3. Implementação B (Por Data de Aquisição)
@Component("SortByAcquiredDate")
public class SortByAcquiredDate implements CollectionSortStrategy {
    @Override
    public Sort getSort() {
        return Sort.by(Sort.Direction.DESC, "acquiredDate");
    }
}
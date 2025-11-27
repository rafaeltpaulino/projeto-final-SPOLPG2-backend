package br.com.ifsp.backend.service.social;


import br.com.ifsp.backend.model.social.CollectionItem;

import java.util.List;

//Strategy para o Collection item
// 1. A Interface (A Estratégia)
public interface CollectionSortStrategy {
    List<CollectionItem> sort(List<CollectionItem> items);
}
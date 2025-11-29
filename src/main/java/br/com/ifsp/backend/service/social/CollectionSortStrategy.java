package br.com.ifsp.backend.service.social;


import br.com.ifsp.backend.model.social.CollectionItem;
import org.springframework.data.domain.Sort;

import java.util.List;

//Strategy para o Collection item
// 1. A Interface (A Estratégia)
public interface CollectionSortStrategy {
    // Retorna a regra de ordenação para o banco de dados
    Sort getSort();
}
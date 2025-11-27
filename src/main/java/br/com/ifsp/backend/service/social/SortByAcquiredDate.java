// 3. Implementação B (Por Data de Aquisição)
@Component("SortByAcquiredDate")
    public class SortByAcquiredDate implements CollectionSortStrategy {
        @Override
        public List sort(List items) {
            return items.stream()
            .sorted(Comparator.comparing(CollectionItem::getAcquiredDate).reversed())
            .collect(Collectors.toList());
        }
    }
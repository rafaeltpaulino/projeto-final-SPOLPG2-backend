// 2. Implementação A (Por Ano de Lançamento)
@Component("SortByYear")
    public class SortByReleaseYear implements CollectionSortStrategy {
        @Override
        public List sort(List items) {
                return items.stream()
                .sorted(Comparator.comparing(item -> item.getRelease().getReleaseYear()))
                .collect(Collectors.toList());
        }
    }
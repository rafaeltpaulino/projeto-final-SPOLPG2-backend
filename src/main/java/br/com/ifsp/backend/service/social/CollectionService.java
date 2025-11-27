// 4. Uso no CollectionService (Contexto)
@Service
    public class CollectionService {

        // O Spring injeta todas as estratégias num Map!
        @Autowired
        private Map sortStrategies;

        public List getMyCollection(Long userId, String sortType) {
            List items = repository.findByUserId(userId);

            // Seleciona a estratégia dinamicamente baseada na string (ex: "SortByYear")
            CollectionSortStrategy strategy = sortStrategies.getOrDefault(sortType, sortStrategies.get("SortByAcquiredDate"));

            return strategy.sort(items);
            }
    }
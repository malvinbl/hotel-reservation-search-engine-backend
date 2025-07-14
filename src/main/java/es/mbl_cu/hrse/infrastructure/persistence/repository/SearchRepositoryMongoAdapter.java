package es.mbl_cu.hrse.infrastructure.persistence.repository;

import es.mbl_cu.hrse.domain.model.Search;
import es.mbl_cu.hrse.domain.repository.SearchRepository;
import es.mbl_cu.hrse.infrastructure.config.CacheConfig;
import es.mbl_cu.hrse.infrastructure.persistence.mapper.SearchEntityMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public class SearchRepositoryMongoAdapter implements SearchRepository {

    private final MongoSearchRepository mongoSearchRepository;
    private final SearchEntityMapper searchEntityMapper;

    public SearchRepositoryMongoAdapter(MongoSearchRepository mongoSearchRepository, SearchEntityMapper searchEntityMapper) {
        this.mongoSearchRepository = mongoSearchRepository;
        this.searchEntityMapper = searchEntityMapper;
    }

    @Cacheable(value = CacheConfig.SEARCH_CACHE)
    @Override
    public Optional<Search> findById(String searchId) {
        return mongoSearchRepository
                .findById(searchId)
                .map(searchEntityMapper::toSearch);
    }

    @Override
    public Optional<Search> findSimilar(String hotelId, LocalDate checkIn, LocalDate checkOut) {
        return mongoSearchRepository
                .findSimilar(hotelId, checkIn, checkOut)
                .map(searchEntityMapper::toSearch);
    }

    @Override
    public void saveWithCounter(Search search, Long counter) {
        var searchEntity = searchEntityMapper.toSearchEntity(search);
        searchEntity.setCounter(counter);
        mongoSearchRepository.save(searchEntity);
    }

}

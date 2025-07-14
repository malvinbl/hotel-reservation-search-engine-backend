package es.mbl_cu.hrse.infrastructure.persistence.repository;

import es.mbl_cu.hrse.domain.repository.CounterIncrementorMongoOperations;
import es.mbl_cu.hrse.domain.vo.Constant;
import es.mbl_cu.hrse.infrastructure.config.CacheConfig;
import es.mbl_cu.hrse.infrastructure.persistence.entity.SearchEntity;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
public class CounterIncrementorMongoOperationsAdapter implements CounterIncrementorMongoOperations {

    private final MongoOperations operations;

    public CounterIncrementorMongoOperationsAdapter(MongoOperations operations) {
        this.operations = operations;
    }

    @CacheEvict(value = CacheConfig.SEARCH_CACHE, key = "#searchId")
    @Override
    public void incCounterBySearchId(String searchId, Long value) {
        var updateCounter = new Update();
        updateCounter.inc(Constant.COUNTER, value);
        var query = new Query(Criteria.where(Constant.ID).is(searchId));
        operations.updateFirst(query, updateCounter, SearchEntity.class);
    }

}

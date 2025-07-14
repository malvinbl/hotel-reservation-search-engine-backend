package es.mbl_cu.hrse.application.service;

import es.mbl_cu.hrse.domain.event.SearchEvent;
import es.mbl_cu.hrse.domain.model.Search;
import es.mbl_cu.hrse.domain.repository.CounterIncrementorMongoOperations;
import es.mbl_cu.hrse.domain.repository.SearchRepository;
import es.mbl_cu.hrse.domain.service.SearchProcessService;
import es.mbl_cu.hrse.domain.vo.Constant;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class SearchProcessServiceImpl implements SearchProcessService {

    private final SearchRepository searchRepository;
    private final CounterIncrementorMongoOperations counterIncrementor;
    private final ApplicationEventPublisher publisher;

    public SearchProcessServiceImpl(SearchRepository searchRepository, CounterIncrementorMongoOperations counterIncrementor, ApplicationEventPublisher publisher) {
        this.searchRepository = searchRepository;
        this.counterIncrementor = counterIncrementor;
        this.publisher = publisher;
    }

    @Override
    public Search process(Search search, String searchId) {
        return searchRepository
                .findSimilar(search.hotelId(), search.checkIn(), search.checkOut())
                .map(similar -> {
                    counterIncrementor.incCounterBySearchId(similar.searchId(), Constant.COUNTER_INCREMENT);
                    return similar;
                })
                .orElseGet(() -> {
                    var newSearch = Search.of(search, searchId);
                    publisher.publishEvent(new SearchEvent(newSearch));
                    return newSearch;
                });
    }

}

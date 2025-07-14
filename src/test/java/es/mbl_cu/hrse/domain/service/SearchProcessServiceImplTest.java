package es.mbl_cu.hrse.domain.service;

import es.mbl_cu.hrse.MotherSearch;
import es.mbl_cu.hrse.application.service.SearchProcessServiceImpl;
import es.mbl_cu.hrse.domain.event.SearchEvent;
import es.mbl_cu.hrse.domain.model.Search;
import es.mbl_cu.hrse.domain.repository.CounterIncrementorMongoOperations;
import es.mbl_cu.hrse.domain.repository.SearchRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SearchProcessServiceImplTest {

    @Mock
    private SearchRepository searchRepository;
    @Mock
    private CounterIncrementorMongoOperations counterIncrementor;
    @Mock
    private ApplicationEventPublisher publisher;

    @InjectMocks
    private SearchProcessServiceImpl searchProcessServiceImpl;

    @Test
    void should_send_message_to_kafka_given_a_new_search() {
        var search = MotherSearch.buildValid();
        var searchId = "searchid1";

        searchProcessServiceImpl.process(search, searchId);

        verify(publisher, times(1)).publishEvent(any(SearchEvent.class));
    }

    @Test
    void should_increment_search_counter() {
        var search = MotherSearch.buildValid();
        var searchId = "searchid1";
        var searchDB = Search.of(search, searchId);

        var hotelId = "1";
        var checkIn = LocalDate.of(2025, 6, 25);
        var checkOut = LocalDate.of(2025, 6, 25);

        Mockito.when(searchRepository.findSimilar(hotelId, checkIn, checkOut)).thenReturn(Optional.of(searchDB));

        searchProcessServiceImpl.process(search, searchId);

        verify(counterIncrementor, times(1)).incCounterBySearchId(anyString(), anyLong());
    }

}
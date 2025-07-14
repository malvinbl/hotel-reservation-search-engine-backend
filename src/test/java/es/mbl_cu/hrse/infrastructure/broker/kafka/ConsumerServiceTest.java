package es.mbl_cu.hrse.infrastructure.broker.kafka;

import es.mbl_cu.hrse.MotherSearch;
import es.mbl_cu.hrse.domain.event.SearchEvent;
import es.mbl_cu.hrse.domain.repository.SearchRepository;
import es.mbl_cu.hrse.domain.vo.Constant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ConsumerServiceTest {

    @Mock
    private SearchRepository searchRepository;

    @InjectMocks
    private ConsumerService consumerService;

    @Test
    void should_consume_event_and_save() {
        var search = MotherSearch.buildValid();
        var searchEvent = new SearchEvent(search);

        consumerService.consume(searchEvent);

        verify(searchRepository).saveWithCounter(search, Constant.COUNTER_INCREMENT);
    }

}
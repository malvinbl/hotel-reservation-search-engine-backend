package es.mbl_cu.hrse.infrastructure.broker.kafka;

import es.mbl_cu.hrse.MotherSearch;
import es.mbl_cu.hrse.domain.event.SearchEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProducerServiceTest {

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    private ProducerService producerService;

    @BeforeEach
    void setUp() {
        producerService = new ProducerService(kafkaTemplate);
        ReflectionTestUtils.setField(producerService, "topic", "search-topic");
    }

    @Test
    void should_send_kafka_message_when_event_is_received() {
        var search = MotherSearch.buildValid();
        var searchEvent = new SearchEvent(search);

        producerService.sendMessage(searchEvent);

        verify(kafkaTemplate).send("search-topic", searchEvent);
    }

}
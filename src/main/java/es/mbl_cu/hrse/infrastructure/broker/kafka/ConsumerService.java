package es.mbl_cu.hrse.infrastructure.broker.kafka;

import es.mbl_cu.hrse.domain.event.SearchEvent;
import es.mbl_cu.hrse.domain.repository.SearchRepository;
import es.mbl_cu.hrse.domain.vo.Constant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ConsumerService {

    private static final Logger log = LogManager.getLogger(ConsumerService.class);

    private final SearchRepository searchRepository;

    public ConsumerService(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    @KafkaListener(topics = "${kafka.topic}")
    public void consume(SearchEvent event) {
        log.info("Message received: [{}].", event);
        searchRepository.saveWithCounter(event.search(), Constant.COUNTER_INCREMENT);
    }

}

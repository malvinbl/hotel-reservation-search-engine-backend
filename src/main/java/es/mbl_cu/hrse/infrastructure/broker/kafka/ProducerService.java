package es.mbl_cu.hrse.infrastructure.broker.kafka;

import es.mbl_cu.hrse.domain.event.SearchEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ProducerService {

    private static final Logger log = LogManager.getLogger(ProducerService.class);
    @Value("${kafka.topic}")
    private String topic;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public ProducerService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Async
    @EventListener
    public void sendMessage(SearchEvent event) {
        kafkaTemplate.send(topic, event);
        log.info("Message sent: [{}].", event);
    }

}

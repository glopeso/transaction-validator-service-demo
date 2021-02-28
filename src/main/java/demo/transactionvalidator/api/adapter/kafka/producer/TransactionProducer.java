package demo.transactionvalidator.api.adapter.kafka.producer;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class TransactionProducer {

    @Value("${transaction-notify.topic}")
    private String transactionEventTopic;

    private final KafkaTemplate kafkaTemplate;

    public TransactionProducer (final KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send (final @RequestBody String message) {
        final String mensageKey = UUID.randomUUID().toString();
        kafkaTemplate.send(transactionEventTopic, mensageKey, message);
    }
}
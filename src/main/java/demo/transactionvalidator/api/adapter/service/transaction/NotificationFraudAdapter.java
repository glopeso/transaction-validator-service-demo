package demo.transactionvalidator.api.adapter.service.transaction;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import demo.transactionvalidator.api.adapter.kafka.producer.TransactionProducer;
import demo.transactionvalidator.api.domain.transaction.TransactionValidation;
import demo.transactionvalidator.api.port.transaction.NotificationFraudPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class NotificationFraudAdapter implements NotificationFraudPort {

    private static final Logger LOG = LoggerFactory.getLogger(NotificationFraudAdapter.class);

    @Autowired
    private TransactionProducer transactionProducer;

    @Async
    @Override
    public void notify (final List<TransactionValidation> validations) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            final String message = objectMapper.writeValueAsString(validations);
            transactionProducer.send(message);
        } catch (JsonProcessingException e) {
            LOG.error("m=notify, status=invalid message={}", e.getMessage());
        }
    }
}

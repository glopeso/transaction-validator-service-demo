package demo.transactionvalidator.api.adapter.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import demo.transactionvalidator.api.port.transaction.TransactionValidatorPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TransactionConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(TransactionConsumer.class);

    private static final String TRANSCATION_CREATED_TOPIC = "topic-transaction-created";

    private static final String APPLICATION_GROUP_ID = "transaction-validator";

    private TransactionValidatorPort transactionValidatorService;

    @Autowired
    public TransactionConsumer (TransactionValidatorPort transactionValidatorService) {
        this.transactionValidatorService = transactionValidatorService;
    }

    @KafkaListener(topics = TRANSCATION_CREATED_TOPIC, groupId = APPLICATION_GROUP_ID)
    public void consumer (String message) {
        LOG.info("m=consumer, status=start, message={}, topic={}, groupId={}", message, TRANSCATION_CREATED_TOPIC,
                APPLICATION_GROUP_ID);
        try {

            transactionValidatorService.createTransactionValidation(getTransactionValidation(message));

            LOG.info("m=consumer, status=finish, topic={}", TRANSCATION_CREATED_TOPIC);

        } catch (JsonProcessingException e) {
            LOG.warn("m=consumer, status=failed, message=Invalid message", e);
        }
    }

    private TransactionEventMessage getTransactionValidation (final String message)
            throws JsonProcessingException {

        final ObjectMapper mapper = new ObjectMapper()
                .enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        return mapper.readValue(message, TransactionEventMessage.class);

    }

}

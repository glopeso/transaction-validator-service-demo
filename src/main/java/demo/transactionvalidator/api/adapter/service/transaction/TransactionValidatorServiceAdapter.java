package demo.transactionvalidator.api.adapter.service.transaction;

import java.util.ArrayList;
import java.util.List;

import demo.transactionvalidator.api.adapter.kafka.consumer.TransactionEventMessage;
import demo.transactionvalidator.api.adapter.repository.transaction.TransactionValidationRepository;
import demo.transactionvalidator.api.adapter.service.transaction.rules.TransactionRulesStrategyFactory;
import demo.transactionvalidator.api.application.transaction.TransactionValidationDTO;
import demo.transactionvalidator.api.domain.transaction.TransactionType;
import demo.transactionvalidator.api.domain.transaction.TransactionValidation;
import demo.transactionvalidator.api.port.transaction.NotificationFraudPort;
import demo.transactionvalidator.api.port.transaction.TransactionValidationRulePort;
import demo.transactionvalidator.api.port.transaction.TransactionValidatorPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionValidatorServiceAdapter implements TransactionValidatorPort {

    private TransactionValidationRepository transactionValidationRepository;

    private NotificationFraudPort notificationFraudPort;

    private TransactionRulesStrategyFactory transactionRulesStrategyFactory;

    @Autowired
    public TransactionValidatorServiceAdapter (
            final TransactionValidationRepository transactionValidationRepository,
            final NotificationFraudPort notificationFraudPort,
            final TransactionRulesStrategyFactory transactionRulesStrategyFactory) {
        this.transactionValidationRepository = transactionValidationRepository;
        this.notificationFraudPort = notificationFraudPort;
        this.transactionRulesStrategyFactory = transactionRulesStrategyFactory;
    }

    @Override
    public void createTransactionValidation (final TransactionEventMessage tem) {
        final TransactionValidationRulePort strategy = transactionRulesStrategyFactory
                .findStrategy(TransactionType.fromValue(tem.getTransactionType()));
        final List<TransactionValidation> validations = strategy.validate(tem);
        validations.forEach(validation -> transactionValidationRepository.save(validation));
        notificationFraudPort.notify(validations);

    }

    @Override
    public List<TransactionValidationDTO> findAll () {
        final Iterable<TransactionValidation> transactions = transactionValidationRepository
                .findAll();

        final List<TransactionValidationDTO> transactionValidationDTOS = new ArrayList<>();

        transactions.forEach(validation -> {
            transactionValidationDTOS.add(getTransactionValidationDTO(validation));
        });

        return transactionValidationDTOS;
    }

    private TransactionValidationDTO getTransactionValidationDTO (final TransactionValidation validation) {
        TransactionValidationDTO transactionValidationDTO = new TransactionValidationDTO();
        transactionValidationDTO.setTransactionId(validation.getTransactionId());
        transactionValidationDTO.setCustomerReceivedId(validation.getCustomerReceivedId());
        transactionValidationDTO.setCustomerSendId(validation.getCustomerSendId());
        transactionValidationDTO.setStatusValidationType(validation.getStatusValidationType());
        transactionValidationDTO.setTransactionType(validation.getTransactionType());
        transactionValidationDTO.setTransactionDate(validation.getTransactionDate());
        return transactionValidationDTO;
    }

}

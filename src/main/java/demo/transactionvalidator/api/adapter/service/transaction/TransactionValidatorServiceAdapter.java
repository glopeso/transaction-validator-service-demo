package demo.transactionvalidator.api.adapter.service.transaction;

import java.util.List;

import demo.transactionvalidator.api.adapter.kafka.consumer.TransactionEventMessage;
import demo.transactionvalidator.api.adapter.repository.transaction.TransactionValidationRepository;
import demo.transactionvalidator.api.domain.transaction.TransactionType;
import demo.transactionvalidator.api.domain.transaction.TransactionValidation;
import demo.transactionvalidator.api.port.transaction.TransactionValidationRuleService;
import demo.transactionvalidator.api.port.transaction.TransactionValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionValidatorServiceAdapter implements TransactionValidatorService {

    private TransactionValidationRepository transactionValidationRepository;

    @Autowired
    public TransactionValidatorServiceAdapter (final TransactionValidationRepository transactionValidationRepository) {
        this.transactionValidationRepository = transactionValidationRepository;
    }

    @Override
    public void createTransactionValidation (final TransactionEventMessage tem) {
        final TransactionValidationRuleService transactionValidationRule = TransactionType
                .fromValue(tem.getTransactionType()).getTransactionValidationRule();
        final List<TransactionValidation> validations = transactionValidationRule.validate(tem);
        validations.forEach(validation -> transactionValidationRepository.save(validation));
    }

}

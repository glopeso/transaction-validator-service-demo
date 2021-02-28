package demo.transactionvalidator.api.adapter.service.transaction.rules;

import java.util.List;

import demo.transactionvalidator.api.adapter.kafka.consumer.TransactionEventMessage;
import demo.transactionvalidator.api.domain.transaction.TransactionValidation;
import demo.transactionvalidator.api.port.transaction.TransactionValidationRuleService;

public class AtmValidationRuleServiceAdpater implements TransactionValidationRuleService {

    @Override
    public List<TransactionValidation> validate (final TransactionEventMessage transactionValidation) {
        return null;
    }
}

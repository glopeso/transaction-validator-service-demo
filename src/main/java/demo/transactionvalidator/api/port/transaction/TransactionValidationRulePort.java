package demo.transactionvalidator.api.port.transaction;

import java.util.List;

import demo.transactionvalidator.api.adapter.kafka.consumer.TransactionEventMessage;
import demo.transactionvalidator.api.domain.transaction.TransactionValidation;

public interface TransactionValidationRulePort {

    List<TransactionValidation> validate (TransactionEventMessage transactionValidation);

}

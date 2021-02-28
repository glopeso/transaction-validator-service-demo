package demo.transactionvalidator.api.port.transaction;

import demo.transactionvalidator.api.adapter.kafka.consumer.TransactionEventMessage;

public interface TransactionValidatorService {

    public void createTransactionValidation (TransactionEventMessage transactionEventMessage);

}

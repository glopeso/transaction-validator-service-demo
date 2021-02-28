package demo.transactionvalidator.api.port.transaction;

import demo.transactionvalidator.api.adapter.kafka.consumer.TransactionEventMessage;

public interface TransactionValidatorPort {

    public void createTransactionValidation (TransactionEventMessage transactionEventMessage);

}

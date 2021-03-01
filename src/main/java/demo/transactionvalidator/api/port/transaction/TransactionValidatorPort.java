package demo.transactionvalidator.api.port.transaction;

import java.util.List;

import demo.transactionvalidator.api.adapter.kafka.consumer.TransactionEventMessage;
import demo.transactionvalidator.api.application.transaction.TransactionValidationDTO;

public interface TransactionValidatorPort {

    void createTransactionValidation (TransactionEventMessage transactionEventMessage);

    List<TransactionValidationDTO> findAll ();

}

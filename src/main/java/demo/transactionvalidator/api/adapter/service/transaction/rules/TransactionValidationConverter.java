package demo.transactionvalidator.api.adapter.service.transaction.rules;

import demo.transactionvalidator.api.adapter.kafka.consumer.TransactionEventMessage;
import demo.transactionvalidator.api.domain.transaction.StatusValidationType;
import demo.transactionvalidator.api.domain.transaction.TransactionType;
import demo.transactionvalidator.api.domain.transaction.TransactionValidation;

public class TransactionValidationConverter {

    public TransactionValidation getTransactionValidation (final TransactionEventMessage tem,
            final StatusValidationType statusValidationType) {
        final TransactionValidation transactionValidation = new TransactionValidation();
        transactionValidation.setTransactionId(tem.getTransactionId());
        transactionValidation.setCustomerReceivedId(tem.getCustomerReceivedId());
        transactionValidation.setCustomerSendId(tem.getCustomerSendId());
        transactionValidation.setTransactionDate(tem.getTransactionDate().toString());
        transactionValidation.setTransactionType(TransactionType.fromValue(tem.getTransactionType()).name());
        transactionValidation.setStatusValidationType(statusValidationType.name());
        return transactionValidation;
    }

}

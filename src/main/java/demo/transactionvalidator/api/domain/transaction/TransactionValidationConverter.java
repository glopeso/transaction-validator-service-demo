package demo.transactionvalidator.api.domain.transaction;

import demo.transactionvalidator.api.adapter.kafka.consumer.TransactionEventMessage;

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

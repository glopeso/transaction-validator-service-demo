package demo.transactionvalidator.api.port.transaction;

import java.util.List;

import demo.transactionvalidator.api.domain.transaction.TransactionValidation;

public interface NotificationFraudPort {

    void notify (List<TransactionValidation> validations);
}

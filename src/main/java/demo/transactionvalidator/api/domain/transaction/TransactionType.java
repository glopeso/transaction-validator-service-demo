package demo.transactionvalidator.api.domain.transaction;

import demo.transactionvalidator.api.adapter.service.transaction.rules.CreditCardValidationRuleServiceAdapter;
import demo.transactionvalidator.api.adapter.service.transaction.rules.MobileBankValidationRuleServiceAdapter;
import demo.transactionvalidator.api.port.transaction.TransactionValidationRulePort;

public enum TransactionType {
    CREDIT_CARD(new CreditCardValidationRuleServiceAdapter()),
    MOBILE_BANK(new MobileBankValidationRuleServiceAdapter());

    private TransactionValidationRulePort transactionValidationRule;

    TransactionType (TransactionValidationRulePort transactionValidationRule) {
        this.transactionValidationRule = transactionValidationRule;
    }

    public TransactionValidationRulePort getTransactionValidationRule () {
        return transactionValidationRule;
    }

    public static TransactionType fromValue (String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return valueOf(value);
    }
}

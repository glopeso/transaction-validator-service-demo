package demo.transactionvalidator.api.domain.transaction;

import demo.transactionvalidator.api.adapter.service.transaction.rules.AtmValidationRuleServiceAdpater;
import demo.transactionvalidator.api.adapter.service.transaction.rules.CreditCardValidationRuleServiceAdapter;
import demo.transactionvalidator.api.adapter.service.transaction.rules.MobileBankValidationRuleServiceAdapter;
import demo.transactionvalidator.api.adapter.service.transaction.rules.TransferValidationRuleServiceAdapter;
import demo.transactionvalidator.api.port.transaction.TransactionValidationRuleService;

public enum TransactionType {
    CREDIT_CARD(new CreditCardValidationRuleServiceAdapter()),
    ATM(new AtmValidationRuleServiceAdpater()),
    TRANSFER(new TransferValidationRuleServiceAdapter()),
    MOBILE_BANK(new MobileBankValidationRuleServiceAdapter());

    private TransactionValidationRuleService transactionValidationRule;

    TransactionType (TransactionValidationRuleService transactionValidationRule) {
        this.transactionValidationRule = transactionValidationRule;
    }

    public TransactionValidationRuleService getTransactionValidationRule () {
        return transactionValidationRule;
    }

    public static TransactionType fromValue (String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return valueOf(value);
    }
}

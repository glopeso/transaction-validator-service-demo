package demo.transactionvalidator.api.domain.transaction;

public enum TransactionType {
    CREDIT_CARD,
    MOBILE_BANK;

    public static TransactionType fromValue (String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return valueOf(value);
    }
}

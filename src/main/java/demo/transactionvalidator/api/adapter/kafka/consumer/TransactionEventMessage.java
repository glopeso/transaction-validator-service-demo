package demo.transactionvalidator.api.adapter.kafka.consumer;

public class TransactionEventMessage {

    private String transactionId;

    private String transactionType;

    private String customerSendId;

    private String customerReceivedId;

    private Long transactionDate;

    private Long transactionValue;

    public TransactionEventMessage () {
    }

    public String getTransactionId () {
        return transactionId;
    }

    public void setTransactionId (final String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionType () {
        return transactionType;
    }

    public void setTransactionType (final String transactionType) {
        this.transactionType = transactionType;
    }

    public String getCustomerSendId () {
        return customerSendId;
    }

    public void setCustomerSendId (final String customerSendId) {
        this.customerSendId = customerSendId;
    }

    public String getCustomerReceivedId () {
        return customerReceivedId;
    }

    public void setCustomerReceivedId (final String customerReceivedId) {
        this.customerReceivedId = customerReceivedId;
    }

    public Long getTransactionDate () {
        return transactionDate;
    }

    public void setTransactionDate (final Long transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Long getTransactionValue () {
        return transactionValue;
    }

    public void setTransactionValue (final Long transactionValue) {
        this.transactionValue = transactionValue;
    }
}

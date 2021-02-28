package demo.transactionvalidator.api.domain.transaction;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import demo.transactionvalidator.api.port.transaction.TransactionValidationRuleService;

@DynamoDBTable(tableName = "transaction-validator")
public class TransactionValidation {

    // Partition key
    @DynamoDBHashKey(attributeName = "transaction_id")
    private String transactionId;

    @DynamoDBIndexHashKey(attributeName = "statusValidationType")
    private String statusValidationType;

    @DynamoDBAttribute(attributeName = "transactionType")
    private String transactionType;

    @DynamoDBAttribute(attributeName = "customerSendId")
    private String customerSendId;

    @DynamoDBAttribute(attributeName = "customerReceivedId")
    private String customerReceivedId;

    @DynamoDBAttribute(attributeName = "transactionDate")
    private String transactionDate;

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

    public String getTransactionDate () {
        return transactionDate;
    }

    public void setTransactionDate (final String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getStatusValidationType () {
        return statusValidationType;
    }

    public void setStatusValidationType (final String statusValidationType) {
        this.statusValidationType = statusValidationType;
    }

    @DynamoDBIgnore
    public TransactionValidationRuleService getValidationRule () {
        final TransactionType transactionType = TransactionType.fromValue(getTransactionType());
        return transactionType.getTransactionValidationRule();
    }

    @Override
    public String toString () {
        return "TransactionValidation"
                + " [transactionId=" + getTransactionId() +
                ",transactionType=" + getTransactionType()
                + ",customerSendId=" + getCustomerSendId()
                + "customerReceivedId=" + getCustomerReceivedId()
                + "transactionDate=" + getTransactionDate() +
                "statusValidationType=" + getStatusValidationType() +
                "]";
    }
}

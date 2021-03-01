package demo.transactionvalidator.api.domain.transaction;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = TransactionValidation.TABLE_NAME)
public class TransactionValidation {

    public static final String TABLE_NAME = "transaction-validator";

    // Partition key
    @DynamoDBHashKey(attributeName = "transaction_id")
    private String transactionId;

    @DynamoDBAttribute(attributeName = "statusValidationType")
    private String statusValidationType;

    @DynamoDBAttribute(attributeName = "transactionType")
    private String transactionType;

    @DynamoDBAttribute(attributeName = "customerSendId")
    private String customerSendId;

    @DynamoDBAttribute(attributeName = "customerReceivedId")
    private String customerReceivedId;

    @DynamoDBAttribute(attributeName = "transactionDate")
    private String transactionDate;

    @DynamoDBAttribute(attributeName = "transactionValidationDate")
    private String transactionValidationDate;

}

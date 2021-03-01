package demo.transactionvalidator.api.domain.restriction;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = CustomerRestriction.TABLE_NAME)
public class CustomerRestriction {

    public static final String TABLE_NAME = "customer-restriction";

    // Partition key
    @DynamoDBHashKey(attributeName = "customer_id")
    private String customerId;

    public CustomerRestriction () {
    }

    public CustomerRestriction (final String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerId () {
        return customerId;
    }

    public void setCustomerId (final String customerId) {
        this.customerId = customerId;
    }

}

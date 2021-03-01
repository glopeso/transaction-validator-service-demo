package demo.transactionvalidator.api.domain.analytics;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = AverageOnline.TABLE_NAME)
public class AverageOnline {

    public static final String TABLE_NAME = "analytics-avg-customer";

    // Partition key
    @DynamoDBHashKey(attributeName = "customer_id")
    private String customerId;

    @DynamoDBAttribute(attributeName = "avg")
    private String avg;

    public AverageOnline () {
    }

    public AverageOnline (final String customerId, final String avg) {
        this.customerId = customerId;
        this.avg = avg;
    }

    public String getCustomerId () {
        return customerId;
    }

    public void setCustomerId (final String customerId) {
        this.customerId = customerId;
    }

    public String getAvg () {
        return avg;
    }

    public void setAvg (final String avg) {
        this.avg = avg;
    }
}

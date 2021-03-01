package demo.transactionvalidator.api.domain.analytics;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = AverageOnline.TABLE_NAME)
public class AverageOnline {

    public static final String TABLE_NAME = "analytics-avg-customer";

    // Partition key
    @DynamoDBHashKey(attributeName = "customer_id")
    private String customerId;

    @DynamoDBAttribute(attributeName = "avg")
    private String avg;

}

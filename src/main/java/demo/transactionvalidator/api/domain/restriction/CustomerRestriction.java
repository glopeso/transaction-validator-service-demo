package demo.transactionvalidator.api.domain.restriction;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = CustomerRestriction.TABLE_NAME)
public class CustomerRestriction {

    public static final String TABLE_NAME = "customer-restriction";

    // Partition key
    @DynamoDBHashKey(attributeName = "customer_id")
    private String customerId;

}

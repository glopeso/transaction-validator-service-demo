package demo.transactionvalidator.api.adapter.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

import java.util.List;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import demo.transactionvalidator.api.TransactionValidatorApiDemoApplication;
import demo.transactionvalidator.api.adapter.repository.transaction.TransactionValidationRepository;
import demo.transactionvalidator.api.domain.transaction.StatusValidationType;
import demo.transactionvalidator.api.domain.transaction.TransactionType;
import demo.transactionvalidator.api.domain.transaction.TransactionValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest(classes = TransactionValidatorApiDemoApplication.class)
@WebAppConfiguration
@ActiveProfiles("local")
@TestPropertySource(properties = {
        "amazon.dynamodb.endpoint=http://localhost:8000/",
        "amazon.aws.accesskey=12345",
        "amazon.aws.secretkey=12345" })
public class TransactionValidationRepositoryIntegrationTest {

    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    TransactionValidationRepository repository;

    @BeforeEach
    public void setup () throws Exception {
        dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
        CreateTableRequest tableRequest = dynamoDBMapper
                .generateCreateTableRequest(TransactionValidation.class);
        tableRequest.setProvisionedThroughput(
                new ProvisionedThroughput(1L, 1L));

        final List<String> tableNames = amazonDynamoDB.listTables().getTableNames();
        if (!tableNames.contains(TransactionValidation.TABLE_NAME)) {
            amazonDynamoDB.createTable(tableRequest);
        }
        dynamoDBMapper.batchDelete(repository.findAll());
    }

    @Test
    public void shouldSaveAndSearchData () {
        TransactionValidation tra = getTransactionValidation();

        repository.save(tra);
        List<TransactionValidation> result = (List<TransactionValidation>) repository.findAll();

        assertThat(result.size(), is(greaterThan(0)));
        assertThat(result.get(0).getCustomerSendId(), is(equalTo(tra.getCustomerSendId())));
    }

    private TransactionValidation getTransactionValidation () {
        final TransactionValidation tem = new TransactionValidation();
        tem.setCustomerSendId("12345");
        tem.setTransactionDate("10000");
        tem.setTransactionType(TransactionType.MOBILE_BANK.name());
        tem.setCustomerReceivedId("5000");
        tem.setTransactionId("1000000");
        tem.setStatusValidationType(StatusValidationType.SUCCESS.name());
        return tem;
    }
}
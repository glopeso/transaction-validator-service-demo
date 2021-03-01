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
import demo.transactionvalidator.api.adapter.repository.analytics.AverageOnlineRepository;
import demo.transactionvalidator.api.domain.analytics.AverageOnline;
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
public class AverageOnlineRepositoryIntegrationTest {

    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    AverageOnlineRepository repository;

    private static final String CUSTOMER = "1000000000";

    private static final String AVG = "150";

    @BeforeEach
    public void setup () throws Exception {
        dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
        CreateTableRequest tableRequest = dynamoDBMapper
                .generateCreateTableRequest(AverageOnline.class);
        tableRequest.setProvisionedThroughput(
                new ProvisionedThroughput(1L, 1L));
        final List<String> tableNames = amazonDynamoDB.listTables().getTableNames();
        if (!tableNames.contains(AverageOnline.TABLE_NAME)) {
            amazonDynamoDB.createTable(tableRequest);
        }
        dynamoDBMapper.batchDelete(repository.findAll());
    }

    @Test
    public void shouldSaveAndSearchData () {
        AverageOnline av = new AverageOnline();
        av.setCustomerId(CUSTOMER);
        av.setAvg(AVG);

        repository.save(av);
        List<AverageOnline> result = (List<AverageOnline>) repository.findAll();

        assertThat(result.size(), is(greaterThan(0)));
        assertThat(result.get(0).getCustomerId(), is(equalTo(CUSTOMER)));
        assertThat(result.get(0).getAvg(), is(equalTo(AVG)));
    }
}
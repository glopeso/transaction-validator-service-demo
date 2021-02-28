package demo.transactionvalidator.api.adapter.service.transaction.rules;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.mockito.Mockito.when;

import java.util.List;

import demo.transactionvalidator.api.adapter.kafka.consumer.TransactionEventMessage;
import demo.transactionvalidator.api.domain.transaction.StatusValidationType;
import demo.transactionvalidator.api.domain.transaction.TransactionType;
import demo.transactionvalidator.api.domain.transaction.TransactionValidation;
import demo.transactionvalidator.api.port.analytics.TransactionDataAnalyticsPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MobileBanKValidationRuleServiceAdapterTest {

    @Mock
    private TransactionDataAnalyticsPort transactionDataAnalytics;

    @InjectMocks
    private MobileBankValidationRuleServiceAdapter mobileBankValidationRuleServiceAdapter;

    @BeforeEach
    public void setUp () {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldGenerateFraudValidationWhenAverageHigher () {
        final TransactionEventMessage tem = getTransactionEventMessage();

        when(transactionDataAnalytics
                .checkValueIsHigherThanAverageOnline(tem.getCustomerSendId(), tem.getTransactionValue()))
                .thenReturn(Boolean.TRUE.booleanValue());

        final List<TransactionValidation> validate = mobileBankValidationRuleServiceAdapter
                .validate(tem);

        Assertions.assertThat(validate.size()).isEqualTo(1);
        assertThat(validate, contains(
                hasProperty("statusValidationType", is(StatusValidationType.HIGHER_AVERAGE_ONLINE.name()))
        ));
    }

    @Test
    public void shouldGenerateSuccessValidationWhenAverageHigher () {
        final TransactionEventMessage tem = getTransactionEventMessage();

        when(transactionDataAnalytics
                .checkValueIsHigherThanAverageOnline(tem.getCustomerSendId(), tem.getTransactionValue()))
                .thenReturn(Boolean.FALSE.booleanValue());

        final List<TransactionValidation> validate = mobileBankValidationRuleServiceAdapter
                .validate(tem);

        Assertions.assertThat(validate.size()).isEqualTo(1);
        assertThat(validate, contains(
                hasProperty("statusValidationType", is(StatusValidationType.SUCCESS.name()))
        ));
    }

    private TransactionEventMessage getTransactionEventMessage () {
        final TransactionEventMessage tem = new TransactionEventMessage();
        tem.setCustomerSendId("12345");
        tem.setTransactionValue(100L);
        tem.setTransactionDate(10000L);
        tem.setTransactionType(TransactionType.MOBILE_BANK.name());
        tem.setCustomerReceivedId("5000");
        tem.setTransactionId("1000000");
        return tem;
    }

}

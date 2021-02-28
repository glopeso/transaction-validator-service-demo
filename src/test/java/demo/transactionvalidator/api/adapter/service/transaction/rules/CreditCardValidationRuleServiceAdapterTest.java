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
import demo.transactionvalidator.api.port.restriction.CustomerRestrictionListPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CreditCardValidationRuleServiceAdapterTest {

    @Mock
    private CustomerRestrictionListPort customerRestrictionListPort;

    @InjectMocks
    private CreditCardValidationRuleServiceAdapter creditCardValidationRuleServiceAdapter;

    @BeforeEach
    public void setUp () {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldGenerateTwoValidationsToReceiverAndSenderRestrictedList () {
        final TransactionEventMessage tem = getTransactionEventMessage();

        when(customerRestrictionListPort
                .customerExistsInRestrictionList(tem.getCustomerSendId()))
                .thenReturn(Boolean.TRUE.booleanValue());

        when(customerRestrictionListPort
                .customerExistsInRestrictionList(tem.getCustomerReceivedId()))
                .thenReturn(Boolean.TRUE.booleanValue());

        final List<TransactionValidation> validations = creditCardValidationRuleServiceAdapter.validate(tem);

        Assertions.assertThat(validations.size()).isEqualTo(2);

    }

    @Test
    public void shouldGenerateOneValidationsToReceiverRestrictedList () {
        final TransactionEventMessage tem = getTransactionEventMessage();

        when(customerRestrictionListPort
                .customerExistsInRestrictionList(tem.getCustomerSendId()))
                .thenReturn(Boolean.FALSE.booleanValue());

        when(customerRestrictionListPort
                .customerExistsInRestrictionList(tem.getCustomerReceivedId()))
                .thenReturn(Boolean.TRUE.booleanValue());

        final List<TransactionValidation> validations = creditCardValidationRuleServiceAdapter.validate(tem);

        Assertions.assertThat(validations.size()).isEqualTo(1);
        assertThat(validations, contains(
                hasProperty("statusValidationType", is(StatusValidationType.RECEIVER_ON_RESTRICTED_LIST.name()))
        ));
    }

    @Test
    public void shouldGenerateOneValidationsToSenderRestrictedList () {
        final TransactionEventMessage tem = getTransactionEventMessage();

        when(customerRestrictionListPort
                .customerExistsInRestrictionList(tem.getCustomerSendId()))
                .thenReturn(Boolean.TRUE.booleanValue());

        when(customerRestrictionListPort
                .customerExistsInRestrictionList(tem.getCustomerReceivedId()))
                .thenReturn(Boolean.FALSE.booleanValue());

        final List<TransactionValidation> validations = creditCardValidationRuleServiceAdapter.validate(tem);

        Assertions.assertThat(validations.size()).isEqualTo(1);
        assertThat(validations, contains(
                hasProperty("statusValidationType", is(StatusValidationType.SENDER_ON_RESTRICTED_LIST.name()))
        ));

    }

    @Test
    public void shouldGenerateSuccessValidations () {
        final TransactionEventMessage tem = getTransactionEventMessage();

        when(customerRestrictionListPort
                .customerExistsInRestrictionList(tem.getCustomerSendId()))
                .thenReturn(Boolean.FALSE.booleanValue());

        when(customerRestrictionListPort
                .customerExistsInRestrictionList(tem.getCustomerReceivedId()))
                .thenReturn(Boolean.FALSE.booleanValue());

        final List<TransactionValidation> validations = creditCardValidationRuleServiceAdapter.validate(tem);

        Assertions.assertThat(validations.size()).isEqualTo(1);
        assertThat(validations, contains(
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

package demo.transactionvalidator.api.adapter.service.transaction;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import demo.transactionvalidator.api.adapter.kafka.consumer.TransactionEventMessage;
import demo.transactionvalidator.api.adapter.repository.transaction.TransactionValidationRepository;
import demo.transactionvalidator.api.adapter.service.transaction.rules.MobileBankValidationRuleServiceAdapter;
import demo.transactionvalidator.api.adapter.service.transaction.rules.TransactionRulesStrategyFactory;
import demo.transactionvalidator.api.application.transaction.TransactionValidationDTO;
import demo.transactionvalidator.api.domain.transaction.TransactionType;
import demo.transactionvalidator.api.domain.transaction.TransactionValidation;
import demo.transactionvalidator.api.port.transaction.NotificationFraudPort;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TransactionValidatorServiceAdapterTest {

    @Mock
    private TransactionValidationRepository transactionValidationRepository;

    @Mock
    private NotificationFraudPort notificationFraudPort;

    @Mock
    private MobileBankValidationRuleServiceAdapter mobileBankValidationRuleServiceAdapter;

    @Mock
    private TransactionRulesStrategyFactory transactionRulesStrategyFactory;

    @InjectMocks
    private TransactionValidatorServiceAdapter transactionValidatorServiceAdapter;

    @BeforeEach
    public void setUp () {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void checkCallProcess () {
        when(transactionRulesStrategyFactory.findStrategy(any()))
                .thenReturn(mobileBankValidationRuleServiceAdapter);

        transactionValidatorServiceAdapter.createTransactionValidation(getTransactionEventMessage());

        verify(transactionValidationRepository, atMost(1)).save(any());
        verify(notificationFraudPort, atMost(1)).notify(anyList());
    }

    @Test
    public void checkFindAllValidations () {
        List<TransactionValidation> validations = new ArrayList<>();
        validations.add(getTransactionValidation());

        when(transactionValidationRepository.findAll()).thenReturn(new Iterable<TransactionValidation>() {

            @Override
            public Iterator<TransactionValidation> iterator () {
                return validations.iterator();
            }
        });

        final List<TransactionValidationDTO> allTransactions = transactionValidatorServiceAdapter.findAll();

        verify(transactionValidationRepository, atMost(1)).findAll();
        Assertions.assertThat(allTransactions.size()).isEqualTo(1);
    }

    private TransactionValidation getTransactionValidation () {
        final TransactionValidation tem = new TransactionValidation();
        tem.setCustomerSendId("12345");
        tem.setTransactionDate("10000");
        tem.setTransactionType(TransactionType.MOBILE_BANK.name());
        tem.setCustomerReceivedId("5000");
        tem.setTransactionId("1000000");
        return tem;
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

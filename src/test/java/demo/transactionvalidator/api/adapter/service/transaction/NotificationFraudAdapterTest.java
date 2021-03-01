package demo.transactionvalidator.api.adapter.service.transaction;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;

import demo.transactionvalidator.api.adapter.kafka.producer.TransactionProducer;
import demo.transactionvalidator.api.domain.transaction.TransactionType;
import demo.transactionvalidator.api.domain.transaction.TransactionValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class NotificationFraudAdapterTest {

    @Mock
    private TransactionProducer transactionProducer;

    @InjectMocks
    private NotificationFraudAdapter notificationFraudAdapter;

    @BeforeEach
    public void setUp () {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldCallServiceSendMessage () {
        final ArrayList<TransactionValidation> validations = new ArrayList<>();
        validations.add(getTransactionEventMessage());
        notificationFraudAdapter.notify(validations);
        verify(transactionProducer, only()).send(anyString());
    }

    private TransactionValidation getTransactionEventMessage () {
        final TransactionValidation tem = new TransactionValidation();
        tem.setCustomerSendId("12345");
        tem.setTransactionDate("10000");
        tem.setTransactionType(TransactionType.MOBILE_BANK.name());
        tem.setCustomerReceivedId("5000");
        tem.setTransactionId("1000000");
        return tem;
    }

}

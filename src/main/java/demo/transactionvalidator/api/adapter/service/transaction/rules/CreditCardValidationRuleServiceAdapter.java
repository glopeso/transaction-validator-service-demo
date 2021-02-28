package demo.transactionvalidator.api.adapter.service.transaction.rules;

import java.util.ArrayList;
import java.util.List;

import demo.transactionvalidator.api.adapter.kafka.consumer.TransactionEventMessage;
import demo.transactionvalidator.api.domain.transaction.StatusValidationType;
import demo.transactionvalidator.api.domain.transaction.TransactionValidation;
import demo.transactionvalidator.api.domain.transaction.TransactionValidationConverter;
import demo.transactionvalidator.api.port.restriction.CustomerRestrictionListPort;
import demo.transactionvalidator.api.port.transaction.TransactionValidationRulePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreditCardValidationRuleServiceAdapter implements TransactionValidationRulePort {

    @Autowired
    private CustomerRestrictionListPort customerRestrictionList;

    @Override
    public List<TransactionValidation> validate (final TransactionEventMessage transactionValidation) {
        List<TransactionValidation> transactionValidations = new ArrayList<>();
        final boolean senderRestrict = customerRestrictionList
                .customerExistsInRestrictionList(transactionValidation.getCustomerSendId());

        final boolean receivedRestrict = customerRestrictionList
                .customerExistsInRestrictionList(transactionValidation.getCustomerReceivedId());

        checkSenderAndReceiver(transactionValidation, transactionValidations, senderRestrict, receivedRestrict);

        if (transactionValidations.isEmpty()) {
            transactionValidations
                    .add(new TransactionValidationConverter().getTransactionValidation(transactionValidation,
                            StatusValidationType.SUCCESS));
        }

        return transactionValidations;
    }

    private void checkSenderAndReceiver (final TransactionEventMessage transactionValidation,
            final List<TransactionValidation> transactionValidations, final boolean senderRestrict,
            final boolean receivedRestrict) {
        if (senderRestrict) {
            transactionValidations.add(new TransactionValidationConverter()
                    .getTransactionValidation(transactionValidation, StatusValidationType.SENDER_ON_RESTRICTED_LIST));
        }

        if (receivedRestrict) {
            transactionValidations.add(new TransactionValidationConverter()
                    .getTransactionValidation(transactionValidation, StatusValidationType.RECEIVER_ON_RESTRICTED_LIST));
        }
    }
}

package demo.transactionvalidator.api.adapter.service.transaction.rules;

import java.util.ArrayList;
import java.util.List;

import demo.transactionvalidator.api.adapter.kafka.consumer.TransactionEventMessage;
import demo.transactionvalidator.api.domain.transaction.StatusValidationType;
import demo.transactionvalidator.api.domain.transaction.TransactionValidation;
import demo.transactionvalidator.api.domain.transaction.TransactionValidationConverter;
import demo.transactionvalidator.api.port.analytics.TransactionDataAnalyticsPort;
import demo.transactionvalidator.api.port.transaction.TransactionValidationRulePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MobileBankValidationRuleServiceAdapter implements TransactionValidationRulePort {

    @Autowired
    private TransactionDataAnalyticsPort transactionDataAnalytics;

    @Override
    public List<TransactionValidation> validate (final TransactionEventMessage transactionValidation) {
        List<TransactionValidation> transactionValidations = new ArrayList<>();
        checkValidationAverageByCustomer(transactionValidation, transactionValidations);

        if (transactionValidations.isEmpty()) {
            transactionValidations
                    .add(new TransactionValidationConverter().getTransactionValidation(transactionValidation,
                            StatusValidationType.SUCCESS));
        }

        return transactionValidations;
    }

    private void checkValidationAverageByCustomer (final TransactionEventMessage transactionValidation,
            final List<TransactionValidation> transactionValidations) {
        final boolean isHigherThanAverageOnlineByCustomerSender = transactionDataAnalytics
                .checkValueIsHigherThanAverageOnline(transactionValidation.getCustomerSendId(),
                        transactionValidation.getTransactionValue());

        if (isHigherThanAverageOnlineByCustomerSender) {
            transactionValidations
                    .add(new TransactionValidationConverter().getTransactionValidation(transactionValidation,
                            StatusValidationType.HIGHER_AVERAGE_ONLINE));
        }
    }

}

package demo.transactionvalidator.api.adapter.service.analytics;

import java.util.Optional;

import demo.transactionvalidator.api.adapter.repository.analytics.AverageOnlineRepository;
import demo.transactionvalidator.api.application.analytics.AnalyticsAverageOnlineVO;
import demo.transactionvalidator.api.domain.analytics.AverageOnline;
import demo.transactionvalidator.api.port.analytics.TransactionDataAnalyticsPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionDataAnalyticsAdapter implements TransactionDataAnalyticsPort {

    private AverageOnlineRepository averageOnlineRepository;

    @Autowired
    public TransactionDataAnalyticsAdapter (
            final AverageOnlineRepository averageOnlineRepository) {
        this.averageOnlineRepository = averageOnlineRepository;
    }

    @Override
    public void createAvgOnline (final AnalyticsAverageOnlineVO analyticsAverageOnlineVO) {
        AverageOnline averageOnline = new AverageOnline(analyticsAverageOnlineVO.getCustomerId(),
                analyticsAverageOnlineVO.getAvg().toString());
        averageOnlineRepository.save(averageOnline);
    }

    @Override
    public boolean checkValueIsHigherThanAverageOnline (final String customerId, final Long valueTransaction) {
        final Optional<AverageOnline> averageOnlineRepositoryById = averageOnlineRepository.findById(customerId);

        if (averageOnlineRepositoryById.isPresent()) {
            final AverageOnline averageOnline = averageOnlineRepositoryById.get();
            final Long avgTransactionOnline = Long.parseLong(averageOnline.getAvg());
            return valueTransaction > avgTransactionOnline;

        }
        return false;
    }
}

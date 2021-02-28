package demo.transactionvalidator.api.port.analytics;

import demo.transactionvalidator.api.application.analytics.AnalyticsAverageOnlineVO;

public interface TransactionDataAnalytics {

    void createAvgOnline (AnalyticsAverageOnlineVO analyticsAverageOnlineVO);

    boolean checkValueIsHigherThanAverageOnline (final String customerId, final Long value);

}

package demo.transactionvalidator.api.application.analytics;

import java.io.Serializable;

public class AnalyticsAverageOnlineVO implements Serializable {

    private String customerId;

    private Long avg;

    public String getCustomerId () {
        return customerId;
    }

    public void setCustomerId (final String customerId) {
        this.customerId = customerId;
    }

    public Long getAvg () {
        return avg;
    }

    public void setAvg (final Long avg) {
        this.avg = avg;
    }
}

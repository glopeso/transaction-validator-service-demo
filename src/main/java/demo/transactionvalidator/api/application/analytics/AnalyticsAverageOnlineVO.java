package demo.transactionvalidator.api.application.analytics;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class AnalyticsAverageOnlineVO implements Serializable {

    @NotNull
    private String customerId;

    @NotNull
    @Positive
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

package demo.transactionvalidator.api.application.analytics;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticsAverageOnlineVO implements Serializable {

    @NotNull
    private String customerId;

    @NotNull
    @Positive
    private Long avg;

}

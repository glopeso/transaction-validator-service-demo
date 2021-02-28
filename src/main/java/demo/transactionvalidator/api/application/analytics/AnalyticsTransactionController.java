package demo.transactionvalidator.api.application.analytics;

import demo.transactionvalidator.api.port.analytics.TransactionDataAnalytics;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/transaction-data-analytics")
public class AnalyticsTransactionController {

    private TransactionDataAnalytics transactionDataAnalytics;

    public AnalyticsTransactionController (
            final TransactionDataAnalytics transactionDataAnalytics) {
        this.transactionDataAnalytics = transactionDataAnalytics;
    }

    @PostMapping
    public ResponseEntity<Void> createAvgOnlineByCustomer (@RequestBody AnalyticsAverageOnlineVO avg) {
        transactionDataAnalytics.createAvgOnline(avg);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}

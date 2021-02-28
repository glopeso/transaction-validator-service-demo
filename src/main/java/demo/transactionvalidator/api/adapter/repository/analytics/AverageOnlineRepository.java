package demo.transactionvalidator.api.adapter.repository.analytics;

import java.util.Optional;

import demo.transactionvalidator.api.domain.analytics.AverageOnline;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface AverageOnlineRepository extends CrudRepository<AverageOnline, String> {

    Optional<AverageOnline> findById (String id);
}
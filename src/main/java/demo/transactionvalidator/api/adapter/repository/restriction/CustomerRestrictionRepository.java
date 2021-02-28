package demo.transactionvalidator.api.adapter.repository.restriction;

import java.util.Optional;

import demo.transactionvalidator.api.domain.restriction.CustomerRestriction;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface CustomerRestrictionRepository extends CrudRepository<CustomerRestriction, String> {

    Optional<CustomerRestriction> findById (String id);
}

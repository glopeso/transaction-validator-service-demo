package demo.transactionvalidator.api.adapter.repository.transaction;

import java.util.Optional;

import demo.transactionvalidator.api.domain.transaction.TransactionValidation;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface TransactionValidationRepository extends CrudRepository<TransactionValidation, String> {

    Optional<TransactionValidation> findById (String id);
}
package demo.transactionvalidator.api.application.transaction;

import java.util.List;

import demo.transactionvalidator.api.port.transaction.TransactionValidatorPort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/transaction-validator")
public class TransactionEventController {

    TransactionValidatorPort transactionValidatorPort;

    public TransactionEventController (
            final TransactionValidatorPort transactionValidatorPort) {
        this.transactionValidatorPort = transactionValidatorPort;
    }

    @GetMapping
    public List<TransactionValidationDTO> findAll () {
        return transactionValidatorPort.findAll();
    }

}
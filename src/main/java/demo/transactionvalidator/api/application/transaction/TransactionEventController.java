package demo.transactionvalidator.api.application.transaction;

import java.util.List;

import javax.validation.Valid;

import demo.transactionvalidator.api.adapter.kafka.producer.TransactionProducer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/transaction-validator")
public class TransactionEventController {
 
    private final TransactionProducer orderProducer;
 
    public TransactionEventController (TransactionProducer orderProducer) {
        this.orderProducer = orderProducer;
    }
 
    @GetMapping
    public List<TransactionValidationDTO> searchValidationByTransactionId(@Valid @RequestParam final String transactionId) {
        return null;
    }

    @GetMapping(path = "/customer")
    public List<TransactionValidationDTO> searchValidationByCustomerId(@Valid @RequestParam final String customerId) {
        return null;
    }
}
package demo.transactionvalidator.api.application;

import demo.transactionvalidator.api.adapter.kafka.producer.TransactionProducer;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/transaction-validator")
public class TransactionEventController {
 
    private final TransactionProducer orderProducer;
 
    public TransactionEventController (TransactionProducer orderProducer) {
        this.orderProducer = orderProducer;
    }
 
    @RequestMapping(method = RequestMethod.POST)
    public void send(@RequestBody String order) {
        orderProducer.send(order);
    }
}
package demo.transactionvalidator.api.application.restriction;

import javax.validation.Valid;

import demo.transactionvalidator.api.port.restriction.CustomerRestrictionListPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/customer-restriction")
public class CustomerRestrictionController {

    private CustomerRestrictionListPort customerRestrictionList;

    public CustomerRestrictionController (
            final CustomerRestrictionListPort customerRestrictionList) {
        this.customerRestrictionList = customerRestrictionList;
    }

    @PostMapping
    public ResponseEntity<Void> addCustomerInRestrictionList (
            @RequestBody @Valid final CustomerRestrictionVO customerRestrictionVO) {
        customerRestrictionList.addCustomerInRestrictionList(customerRestrictionVO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}

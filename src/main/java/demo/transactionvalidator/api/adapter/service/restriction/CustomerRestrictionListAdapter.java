package demo.transactionvalidator.api.adapter.service.restriction;

import demo.transactionvalidator.api.adapter.repository.restriction.CustomerRestrictionRepository;
import demo.transactionvalidator.api.application.restriction.CustomerRestrictionVO;
import demo.transactionvalidator.api.domain.restriction.CustomerRestriction;
import demo.transactionvalidator.api.port.restriction.CustomerRestrictionListPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerRestrictionListAdapter implements CustomerRestrictionListPort {

    @Autowired
    private CustomerRestrictionRepository customerRestrictionRepository;

    @Override
    public void addCustomerInRestrictionList (final CustomerRestrictionVO customerRestrictionVO) {
        CustomerRestriction customerRestriction = new CustomerRestriction(customerRestrictionVO.getCustomerId());
        customerRestrictionRepository.save(customerRestriction);
    }

    @Override
    public boolean customerExistsInRestrictionList (final String customerId) {
        return customerRestrictionRepository.findById(customerId).isPresent();
    }
}

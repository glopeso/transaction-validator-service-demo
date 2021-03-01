package demo.transactionvalidator.api.adapter.service.restriction;

import java.util.ArrayList;
import java.util.List;

import demo.transactionvalidator.api.adapter.repository.restriction.CustomerRestrictionRepository;
import demo.transactionvalidator.api.application.restriction.CustomerRestrictionDTO;
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

    @Override
    public List<CustomerRestrictionDTO> findAll () {
        final Iterable<CustomerRestriction> customers = customerRestrictionRepository.findAll();
        List<CustomerRestrictionDTO> coCustomerRestrictionDTOS = new ArrayList<>();
        customers.forEach(customerRestriction -> coCustomerRestrictionDTOS
                .add(new CustomerRestrictionDTO(customerRestriction.getCustomerId())));

        return coCustomerRestrictionDTOS;
    }
}

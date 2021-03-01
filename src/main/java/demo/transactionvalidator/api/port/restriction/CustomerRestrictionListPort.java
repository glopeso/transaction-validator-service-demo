package demo.transactionvalidator.api.port.restriction;

import java.util.List;

import demo.transactionvalidator.api.application.restriction.CustomerRestrictionDTO;
import demo.transactionvalidator.api.application.restriction.CustomerRestrictionVO;

public interface CustomerRestrictionListPort {

    void addCustomerInRestrictionList (CustomerRestrictionVO customerRestrictionVO);

    boolean customerExistsInRestrictionList (String customerId);

    List<CustomerRestrictionDTO> findAll ();

}

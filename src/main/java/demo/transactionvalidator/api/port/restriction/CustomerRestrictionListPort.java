package demo.transactionvalidator.api.port.restriction;

import demo.transactionvalidator.api.application.restriction.CustomerRestrictionVO;

public interface CustomerRestrictionListPort {

    void addCustomerInRestrictionList (CustomerRestrictionVO customerRestrictionVO);

    boolean customerExistsInRestrictionList (String customerId);
}

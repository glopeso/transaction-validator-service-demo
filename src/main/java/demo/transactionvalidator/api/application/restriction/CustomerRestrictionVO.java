package demo.transactionvalidator.api.application.restriction;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

public class CustomerRestrictionVO implements Serializable {

    @NotNull
    private String customerId;

    public String getCustomerId () {
        return customerId;
    }

    public void setCustomerId (final String customerId) {
        this.customerId = customerId;
    }
}

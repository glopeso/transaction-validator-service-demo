package demo.transactionvalidator.api.application.restriction;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRestrictionDTO implements Serializable {

    private String customerId;

}

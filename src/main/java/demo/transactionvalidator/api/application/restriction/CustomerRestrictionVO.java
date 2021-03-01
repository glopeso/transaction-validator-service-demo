package demo.transactionvalidator.api.application.restriction;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRestrictionVO implements Serializable {

    @NotNull
    private String customerId;

}

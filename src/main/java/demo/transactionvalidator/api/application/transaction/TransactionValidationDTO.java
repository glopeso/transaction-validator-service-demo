package demo.transactionvalidator.api.application.transaction;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionValidationDTO implements Serializable {

    private String transactionId;

    private String statusValidationType;

    private String transactionType;

    private String customerSendId;

    private String customerReceivedId;

    private String transactionDate;

}

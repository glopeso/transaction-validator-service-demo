package demo.transactionvalidator.api.adapter.http.base;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse implements Serializable {

    private static final long serialVersionUID = -4290029190409909225L;

    private Date timestamp;

    private String status;

    private String message;

    private List<Field> fields;

    @Getter
    @Builder
    public static class Field {

        private String name;

        private String message;
    }
}
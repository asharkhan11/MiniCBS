package in.banking.cbs.action_service.message;

import in.banking.cbs.action_service.utility.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Response<T> {

    private ResponseStatus status;
    private String message;
    private T data;

}

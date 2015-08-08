package LoyaltyPlant;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by JaneJava on 5/4/15.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ApplicationException extends RuntimeException{
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ApplicationException(String message) {
        this.message = message;
    }
}

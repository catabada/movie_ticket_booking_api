package vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseException extends Exception {
    public BaseException(String message) {
        super(message);
    }

}

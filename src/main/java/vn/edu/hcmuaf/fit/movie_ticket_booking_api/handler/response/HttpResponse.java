package vn.edu.hcmuaf.fit.movie_ticket_booking_api.handler.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
public class HttpResponse {
    private Boolean success;
    private HttpStatus httpStatus;
    private int httpStatusCode;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private ZonedDateTime timestamp;

    public HttpResponse(AbstractBuilder<?> builder) {
        this.success = builder.success;
        this.httpStatus = builder.httpStatus;
        this.httpStatusCode = builder.httpStatusCode;
        this.timestamp = builder.timestamp;
    }

    public abstract static class AbstractBuilder<B extends AbstractBuilder<B>> {
        protected Boolean success;
        protected HttpStatus httpStatus;
        protected int httpStatusCode;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
        protected ZonedDateTime timestamp;

        public AbstractBuilder() {
            this.timestamp = ZonedDateTime.now();
        }

        public AbstractBuilder(final HttpStatus httpStatus, final Boolean success) {
            this.timestamp = ZonedDateTime.now();
            this.httpStatus = httpStatus;
            this.httpStatusCode = httpStatus.value();
            this.success = success;
        }
        public B success(final Boolean success) {
            this.success = success;
            return self();
        }

        public B httpStatus(final HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
            this.httpStatusCode = httpStatus.value();
            return self();
        }
        public abstract HttpResponse build();

        protected abstract B self();
    }

}
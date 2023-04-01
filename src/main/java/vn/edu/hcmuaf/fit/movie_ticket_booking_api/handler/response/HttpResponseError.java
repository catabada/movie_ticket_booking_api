package vn.edu.hcmuaf.fit.movie_ticket_booking_api.handler.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class HttpResponseError extends HttpResponse {
    private String reason;
    private String message;

    public HttpResponseError(Builder builder) {
        super(builder);
        this.message = builder.message;
        this.reason = builder.reason;
    }
    public static Builder builder() {
        return new Builder();
    }

    public static Builder error() {
        return builder().success(Boolean.FALSE);
    }

    public static Builder error(HttpStatus httpStatus, String message) {
        return builder().httpStatus(httpStatus).message(message).success(Boolean.FALSE);
    }

    public static Builder error(HttpStatus httpStatus, String reason, String message) {
        return builder().httpStatus(httpStatus).reason(reason).message(message).success(Boolean.FALSE);
    }

    public static class Builder extends AbstractBuilder<Builder> {
        protected String reason;
        protected String message;

        protected Builder() {
            super();
        }

        protected Builder(final HttpStatus httpStatus, final Boolean isSuccess) {
            super(httpStatus, isSuccess);
        }

        public Builder reason(final String reason) {
            this.reason = reason;
            return this;
        }

        public Builder message(final String message) {
            this.message = message;
            return self();
        }

        @Override
        public HttpResponseError build() {
            return new HttpResponseError(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }


}

package vn.edu.hcmuaf.fit.movie_ticket_booking_api.handler.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class HttpResponseSuccess extends HttpResponse {
    private Object data;
    private String message;

    public HttpResponseSuccess(Builder builder) {
        super(builder);
        this.data = builder.data;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder success() {
        return builder().success().data("Success!").success(Boolean.TRUE);
    }

    public static Builder success(Object object) {
        return builder().success().data(object);
    }

    public static Builder success(Object object, String message) {
        return builder().success().data(object).message(message);
    }

    public static Builder success(String message) {
        return builder().success().message(message);
    }

    public static class Builder extends AbstractBuilder<Builder> {
        protected Object data;
        protected String message;

        protected Builder() {
            super();
        }

        protected Builder(final HttpStatus httpStatus, final Boolean isSuccess) {
            super(httpStatus, isSuccess);
        }

        public Builder data(final Object data) {
            this.data = data;
            return self();
        }

        public Builder message(final String message) {
            this.message = message;
            return self();
        }

        public Builder success() {
            return new Builder(HttpStatus.OK, true);
        }

        @Override
        public HttpResponseSuccess build() {
            return new HttpResponseSuccess(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }

}

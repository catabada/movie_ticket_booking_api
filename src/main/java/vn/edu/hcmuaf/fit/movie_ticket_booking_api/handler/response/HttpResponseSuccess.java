package vn.edu.hcmuaf.fit.movie_ticket_booking_api.handler.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.handler.response.HttpResponse;

@Getter
@Setter
public class HttpResponseSuccess extends HttpResponse {
    private Object data;

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

    public static class Builder extends AbstractBuilder<Builder> {
        protected Object data;

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

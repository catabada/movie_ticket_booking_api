package vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant;

public enum PaymentMethod {
    MOMO("MOMO"),
    VNPAY("VNPAY");

    private final String value;

    PaymentMethod(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

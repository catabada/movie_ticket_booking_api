package vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant;

public enum TicketStatus {
    BOOKED("BOOKED"),
    CANCELLED("CANCELLED"),
    USED("USED");

    private final String value;

    TicketStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

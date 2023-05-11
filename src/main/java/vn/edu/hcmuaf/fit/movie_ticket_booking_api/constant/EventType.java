package vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant;

public enum EventType {
    ADVERTISEMENT("ADVERTISEMENT"),
    PROMOTION("PROMOTION");

    private final String value;

    EventType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

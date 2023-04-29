package vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant;

public enum RoomState {
    AVAILABLE("AVAILABLE"),
    OCCUPIED("OCCUPIED"),
    RESERVED("RESERVED"),
    OUT_OF_SERVICE("OUT_OF_SERVICE"),
    CLEANING("CLEANING"),
    PAUSED("PAUSED");

    private final String value;

    RoomState(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

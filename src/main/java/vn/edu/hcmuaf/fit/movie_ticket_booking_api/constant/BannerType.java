package vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant;

public enum BannerType {
    MOVIE("MOVIE"),
    EVENT("EVENT");

    private final String value;

    BannerType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

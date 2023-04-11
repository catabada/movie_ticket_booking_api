package vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant;

public enum BranchStatus {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    private final String value;

    BranchStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

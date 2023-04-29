package vn.edu.hcmuaf.fit.movie_ticket_booking_api.shared.constant;

/* Language */
public enum Language {
    VIETNAMESE("vi", "vn"),
    ENGLISH("en", "vn");

    private final String language;
    private final String locale;

    Language(String language, String locale) {
        this.language = language;
        this.locale = locale;
    }

    public static Language findByName(String name) {
        for (Language type : values()) {
            if (type.language().equals(name)) {
                return type;
            }
        }
        return null;
    }

    public String language() {
        return language;
    }

    public String locale() {
        return locale;
    }
}

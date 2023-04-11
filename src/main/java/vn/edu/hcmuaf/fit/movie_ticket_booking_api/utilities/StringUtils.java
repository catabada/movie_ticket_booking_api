package vn.edu.hcmuaf.fit.movie_ticket_booking_api.utilities;

import java.text.Normalizer;

public class StringUtils {
    public static String normalizeUri(String input) {
        return stripAccents(input.replaceAll("đ", "d")
                .replace("Đ", "D"))
                .trim().replaceAll("[^a-zA-Z0-9]+", "-").toLowerCase();
    }

    private static String stripAccents(String s) {
        return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}", "");
    }
}

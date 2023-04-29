package vn.edu.hcmuaf.fit.movie_ticket_booking_api.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static Long GetCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    public static String format(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }
}

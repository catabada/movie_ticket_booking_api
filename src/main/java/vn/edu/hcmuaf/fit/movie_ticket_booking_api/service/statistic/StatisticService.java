package vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.statistic;

import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.statistic.StatisticFilter;

import java.util.List;
import java.util.Map;

public interface StatisticService {
    Map<Integer, Long> getStatistic(StatisticFilter filter);
}

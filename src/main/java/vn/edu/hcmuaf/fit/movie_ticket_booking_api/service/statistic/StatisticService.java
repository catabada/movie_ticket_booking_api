package vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.statistic;

import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.statistic.StatisticFilter;

import java.util.List;

public interface StatisticService {
    List<Long> getStatistic(StatisticFilter filter);
}

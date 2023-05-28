package vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.statistic;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class StatisticFilter {
    private StatisticValue value;
    private StatisticTimeline timeline;
}

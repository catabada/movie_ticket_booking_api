package vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.statistic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.invoice.InvoiceSearch;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.statistic.StatisticFilter;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Invoice;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.invoice.InvoiceRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class StatisticServiceImpl implements StatisticService {
    private final InvoiceRepository invoiceRepository;

    @Autowired
    public StatisticServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public Map<Integer, Long> getStatistic(StatisticFilter filter) {
        Map<Integer, Long> result = new HashMap<>();

        switch (filter.getValue()) {
            case REVENUE -> {
                return invoiceRepository.getTotalRevenue(filter.getTimeline());
            }
            case TICKET -> {
                return invoiceRepository.getTotalTicket(filter.getTimeline());
            }
            case MOVIE -> {
                return invoiceRepository.getRevenueByMovie(filter.getTimeline(), filter.getMovieId());
            }
            case BRANCH -> {
                return invoiceRepository.getRevenueByBranch(filter.getTimeline(), filter.getBranchId());
            }
        }

        return result;
    }
}

package vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.statistic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.invoice.InvoiceSearch;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.statistic.StatisticFilter;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Invoice;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.invoice.InvoiceRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class StatisticServiceImpl implements StatisticService {
    private final InvoiceRepository invoiceRepository;

    @Autowired
    public StatisticServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public List<Long> getStatistic(StatisticFilter filter) {
        List<Long> result = new ArrayList<>();

        switch (filter.getValue()) {
            case REVENUE -> {
                return invoiceRepository.getTotalRevenue(filter.getTimeline());
            }
        }

        return result;
    }
}

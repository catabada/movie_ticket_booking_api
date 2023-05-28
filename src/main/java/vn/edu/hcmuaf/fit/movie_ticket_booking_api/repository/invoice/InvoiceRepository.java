package vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.invoice;

import org.springframework.data.repository.NoRepositoryBean;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.invoice.InvoiceSearch;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.statistic.StatisticTimeline;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Invoice;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.ICustomRepository;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface InvoiceRepository extends ICustomRepository<Invoice, Long> {
    Optional<Invoice> findByCode(String code);
    List<Invoice> search(InvoiceSearch search);
    List<Long> getTotalRevenue(StatisticTimeline timeline);
    List<Long> getRevenueByMovie(StatisticTimeline timeline, long movieId);
    List<Long> getRevenueByBranch(StatisticTimeline timeline);
}

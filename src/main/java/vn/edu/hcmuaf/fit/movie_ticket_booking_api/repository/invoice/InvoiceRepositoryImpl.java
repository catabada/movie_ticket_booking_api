package vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.invoice;

import com.querydsl.core.BooleanBuilder;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.ObjectState;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.invoice.InvoiceSearch;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.AbstractCustomRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class InvoiceRepositoryImpl extends AbstractCustomRepository<Invoice, Long> implements InvoiceRepository {
    private final QInvoice qInvoice = QInvoice.invoice;
//    private final QInvoiceCombo qInvoiceCombo = QInvoiceCombo.invoiceCombo;
//    private final QShowtime qShowtime = QShowtime.showtime;
//    private final QTicket qTicket = QTicket.ticket;

    protected InvoiceRepositoryImpl(EntityManager entityManager) {
        super(Invoice.class, entityManager);
    }


    @Override
    public void saveAll() {

    }

    @Override
    public Optional<Invoice> findByCode(String code) {
        return Optional.ofNullable(queryFactory.selectFrom(qInvoice)
                .where(qInvoice.code.eq(code).and(qInvoice.state.ne(ObjectState.DELETED)))
                .fetchFirst());
    }

    @Override
    public List<Invoice> search(InvoiceSearch search) {
        return queryFactory.selectFrom(qInvoice)
                .where(buildSearchCondition(search))
                .fetch();
    }

    private BooleanBuilder buildSearchCondition(InvoiceSearch search) {
        BooleanBuilder builder = new BooleanBuilder();

        if (!ObjectUtils.isEmpty(search.getCode())) {
            builder.and(qInvoice.code.containsIgnoreCase(search.getCode()));
        }

        if (!ObjectUtils.isEmpty(search.getUserId())) {
            builder.and(qInvoice.appUser.id.eq(search.getUserId()));
        }

        if (!ObjectUtils.isEmpty(search.getEmail())) {
            builder.and(qInvoice.appUser.email.containsIgnoreCase(search.getEmail()));
        }

        if (!ObjectUtils.isEmpty(search.getPaymentMethod())) {
            builder.and(qInvoice.paymentMethod.eq(search.getPaymentMethod()));
        }

        if (!ObjectUtils.isEmpty(search.getPaymentStatus())) {
            builder.and(qInvoice.paymentStatus.eq(search.getPaymentStatus()));
        }

        return builder.and(qInvoice.state.ne(ObjectState.DELETED));
    }
}

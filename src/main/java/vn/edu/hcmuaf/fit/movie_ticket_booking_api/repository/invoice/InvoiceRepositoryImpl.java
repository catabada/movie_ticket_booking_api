package vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.invoice;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.DateExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import jakarta.persistence.EntityManager;
import org.hibernate.sql.ast.tree.expression.SqlSelectionExpression;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.ObjectState;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.invoice.InvoiceSearch;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.statistic.StatisticTimeline;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.AbstractCustomRepository;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class InvoiceRepositoryImpl extends AbstractCustomRepository<Invoice, Long> implements InvoiceRepository {
    private final QInvoice qInvoice = QInvoice.invoice;
    //    private final QInvoiceCombo qInvoiceCombo = QInvoiceCombo.invoiceCombo;
    private final QShowtime qShowtime = QShowtime.showtime;
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
                .innerJoin(qInvoice.showtime, qShowtime).fetchJoin()
                .where(buildSearchCondition(search))
                .fetch();
    }

    @Override
    public List<Long> getTotalRevenue(StatisticTimeline timeline) {
        ZonedDateTime now = ZonedDateTime.now();
        NumberExpression<Integer> expression = qInvoice.createdDate.dayOfYear();
        switch (timeline) {
            // this week
            case WEEK -> {
                return queryFactory.select(qInvoice.totalPrice.sum())
                        .from(qInvoice)
                        .where(qInvoice.state.ne(ObjectState.DELETED),
                                qInvoice.createdDate.year().eq(now.getYear()),
                                qInvoice.createdDate.week().eq(now.get(ChronoField.ALIGNED_WEEK_OF_YEAR)))
                        .groupBy(qInvoice.createdDate.dayOfWeek().castToNum(Integer.class))
                        .stream().map(
                                o -> ObjectUtils.isEmpty(o) ? 0L : o
                        ).toList();
            }
            // this month
            case MONTH -> {
                return queryFactory.select(qInvoice.totalPrice.sum())
                        .from(qInvoice)
                        .where(qInvoice.state.ne(ObjectState.DELETED),
                                qInvoice.createdDate.year().eq(now.getYear()),
                                qInvoice.createdDate.month().eq(now.getMonthValue()))
                        .groupBy(qInvoice.createdDate.dayOfMonth())
                        .fetch();
            }
            // this year
            case YEAR -> {
                return queryFactory.select(qInvoice.totalPrice.sum())
                        .from(qInvoice)
                        .where(qInvoice.state.ne(ObjectState.DELETED),
                                qInvoice.createdDate.year().eq(now.getYear()))
                        .groupBy(qInvoice.createdDate.yearMonth())
                        .fetch();
            }
            // today
            default -> {
                return queryFactory.select(qInvoice.totalPrice.sum())
                        .from(qInvoice)
                        .where(qInvoice.state.ne(ObjectState.DELETED),
                                qInvoice.createdDate.year().eq(now.getYear()),
                                qInvoice.createdDate.month().eq(now.getMonthValue()),
                                qInvoice.createdDate.dayOfMonth().eq(now.getDayOfMonth()))
                        .fetch();
            }
        }
    }

    @Override
    public List<Long> getRevenueByMovie(StatisticTimeline timeline, long movieId) {
        return null;
    }

    @Override
    public List<Long> getRevenueByBranch(StatisticTimeline timeline) {
        return null;
    }

    private BooleanBuilder buildSearchCondition(InvoiceSearch search) {
        BooleanBuilder builder = new BooleanBuilder();

        if (!ObjectUtils.isEmpty(search.getCode())) {
            builder.and(qInvoice.code.containsIgnoreCase(search.getCode()));
        }

        if (!ObjectUtils.isEmpty(search.getStartTime())) {
            builder.and(
                    qShowtime.startTime.dayOfMonth().eq(search.getStartTime().getDayOfMonth())
                            .and(qShowtime.startTime.month().eq(search.getStartTime().getMonthValue()))
                            .and(qShowtime.startTime.year().eq(search.getStartTime().getYear()))
                            .and(qShowtime.startTime.hour().eq(search.getStartTime().getHour()))
                            .and(qShowtime.startTime.minute().eq(search.getStartTime().getMinute()))
                            .and(qShowtime.startTime.second().eq(search.getStartTime().getSecond()))
            );
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

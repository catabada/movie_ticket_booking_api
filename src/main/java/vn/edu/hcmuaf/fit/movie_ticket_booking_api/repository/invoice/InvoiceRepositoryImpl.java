package vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.invoice;

import com.querydsl.core.BooleanBuilder;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.ObjectState;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.PaymentStatus;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.invoice.InvoiceSearch;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.statistic.StatisticTimeline;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.AbstractCustomRepository;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.*;

@Repository
public class InvoiceRepositoryImpl extends AbstractCustomRepository<Invoice, Long> implements InvoiceRepository {
    private final QInvoice qInvoice = QInvoice.invoice;
    private final QMovie qMovie = QMovie.movie;
    //    private final QInvoiceCombo qInvoiceCombo = QInvoiceCombo.invoiceCombo;
    private final QShowtime qShowtime = QShowtime.showtime;
    private final QTicket qTicket = QTicket.ticket;
    private final QRoom qRoom = QRoom.room;
    private final QBranch qBranch = QBranch.branch;

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
    public Map<Integer, Long> getTotalRevenue(StatisticTimeline timeline) {
        ZonedDateTime now = ZonedDateTime.now();
        Map<Integer, Long> map = new HashMap<>();

        switch (timeline) {
            case DAY -> {
                map.put(1, 0L);
                queryFactory.select(qInvoice.paymentDate, qInvoice.totalPrice.sum())
                        .from(qInvoice)
                        .where(
                                qInvoice.state.ne(ObjectState.DELETED),
                                qInvoice.paymentStatus.eq(PaymentStatus.SUCCESS),
                                qInvoice.paymentDate.year().eq(now.getYear()),
                                qInvoice.paymentDate.month().eq(now.getMonthValue()),
                                qInvoice.paymentDate.dayOfMonth().eq(now.getDayOfMonth()))
                        .groupBy(qInvoice.paymentDate)
                        .fetch()
                        .stream().forEach(e -> {
                            map.put(1, e.get(1, Long.class) + map.get(1));
                        });

                return map;
            }
            // this week
            case WEEK -> {
                for (int i = 1; i <= 7; i++) {
                    map.put(i, 0L);
                }
                queryFactory.select(qInvoice.paymentDate, qInvoice.totalPrice.sum())
                        .from(qInvoice)
                        .where(
                                qInvoice.state.ne(ObjectState.DELETED),
                                qInvoice.paymentStatus.eq(PaymentStatus.SUCCESS),
                                qInvoice.paymentDate.year().eq(now.getYear()),
                                qInvoice.paymentDate.week().eq(now.get(ChronoField.ALIGNED_WEEK_OF_YEAR) - 1))
                        .groupBy(qInvoice.paymentDate)
                        .fetch()
                        .stream().forEach(e -> {
                            map.put(e.get(0, ZonedDateTime.class).getDayOfWeek().getValue(), e.get(1, Long.class) + map.get(e.get(0, ZonedDateTime.class).getDayOfWeek().getValue()));
                        });

                return map;
            }
            // this month
            case MONTH -> {
                for (int i = 1; i <= now.getMonth().length(false); i++) {
                    map.put(i, 0L);
                }

                queryFactory.select(qInvoice.paymentDate, qInvoice.totalPrice.sum())
                        .from(qInvoice)
                        .where(
                                qInvoice.state.ne(ObjectState.DELETED),
                                qInvoice.paymentStatus.eq(PaymentStatus.SUCCESS),
                                qInvoice.paymentDate.year().eq(now.getYear()),
                                qInvoice.paymentDate.month().eq(now.getMonthValue()))
                        .groupBy(qInvoice.paymentDate)
                        .fetch()
                        .stream().forEach(e -> {
                            map.put(e.get(0, ZonedDateTime.class).getDayOfMonth(), e.get(1, Long.class) + map.get(e.get(0, ZonedDateTime.class).getDayOfMonth()));
                        });

                return map;
            }
//            // this year
            case YEAR -> {
                for (int i = 1; i <= 12; i++) {
                    map.put(i, 0L);
                }

                queryFactory.select(qInvoice.paymentDate, qInvoice.totalPrice.sum())
                        .from(qInvoice)
                        .where(
                                qInvoice.state.ne(ObjectState.DELETED),
                                qInvoice.paymentStatus.eq(PaymentStatus.SUCCESS),
                                qInvoice.paymentDate.year().eq(now.getYear()))
                        .groupBy(qInvoice.paymentDate)
                        .fetch()
                        .stream().forEach(e -> {
                            map.put(e.get(0, ZonedDateTime.class).getMonthValue(), e.get(1, Long.class) + map.get(e.get(0, ZonedDateTime.class).getMonthValue()));
                        });
                return map;
            }
            // today
            default -> {
                return Map.of();
            }
        }
    }

    @Override
    public Map<Integer, Long> getTotalTicket(StatisticTimeline timeline) {
        ZonedDateTime now = ZonedDateTime.now();
        Map<Integer, Long> map = new HashMap<>();

        switch (timeline) {
            case DAY -> {
                map.put(1, 0L);

                queryFactory.select(qInvoice.paymentDate, qTicket.count())
                        .from(qInvoice)
                        .innerJoin(qInvoice.tickets, qTicket)
                        .where(
                                qInvoice.state.ne(ObjectState.DELETED),
                                qInvoice.paymentStatus.eq(PaymentStatus.SUCCESS),
                                qInvoice.paymentDate.year().eq(now.getYear()),
                                qInvoice.paymentDate.month().eq(now.getMonthValue()),
                                qInvoice.paymentDate.dayOfMonth().eq(now.getDayOfMonth()))
                        .groupBy(qInvoice.paymentDate)
                        .fetch()
                        .stream().forEach(e -> {
                            map.put(1, e.get(1, Long.class) + map.get(1));
                        });

                return map;
            }
            // this week
            case WEEK -> {
                for (int i = 1; i <= 7; i++) {
                    map.put(i, 0L);
                }
                queryFactory.select(qInvoice.paymentDate, qTicket.count())
                        .from(qInvoice)
                        .innerJoin(qInvoice.tickets, qTicket)
                        .where(
                                qInvoice.state.ne(ObjectState.DELETED),
                                qInvoice.paymentStatus.eq(PaymentStatus.SUCCESS),
                                qInvoice.paymentDate.year().eq(now.getYear()),
                                qInvoice.paymentDate.week().eq(now.get(ChronoField.ALIGNED_WEEK_OF_YEAR) - 1))
                        .groupBy(qInvoice.paymentDate)
                        .fetch()
                        .stream().forEach(e -> {
                            map.put(e.get(0, ZonedDateTime.class).getDayOfWeek().getValue(),
                                    e.get(1, Long.class) + map.get(e.get(0, ZonedDateTime.class).getDayOfWeek().getValue()));
                        });

                return map;
            }
            // this month
            case MONTH -> {
                for (int i = 1; i <= now.getMonth().length(false); i++) {
                    map.put(i, 0L);
                }

                queryFactory.select(qInvoice.paymentDate, qTicket.count())
                        .from(qInvoice)
                        .innerJoin(qInvoice.tickets, qTicket)
                        .where(
                                qInvoice.state.ne(ObjectState.DELETED),
                                qInvoice.paymentStatus.eq(PaymentStatus.SUCCESS),
                                qInvoice.paymentDate.year().eq(now.getYear()),
                                qInvoice.paymentDate.month().eq(now.getMonthValue()))
                        .groupBy(qInvoice.paymentDate)
                        .fetch()
                        .stream().forEach(e -> {
                            map.put(e.get(0, ZonedDateTime.class).getDayOfMonth(),
                                    e.get(1, Long.class) + map.get(e.get(0, ZonedDateTime.class).getDayOfMonth()));
                        });
                return map;
            }
//            // this year
            case YEAR -> {
                for (int i = 1; i <= 12; i++) {
                    map.put(i, 0L);
                }

                queryFactory.select(qInvoice.paymentDate, qTicket.count())
                        .from(qInvoice)
                        .innerJoin(qInvoice.tickets, qTicket)
                        .where(
                                qInvoice.state.ne(ObjectState.DELETED),
                                qInvoice.paymentStatus.eq(PaymentStatus.SUCCESS),
                                qInvoice.paymentDate.year().eq(now.getYear()))
                        .groupBy(qInvoice.paymentDate)
                        .fetch()
                        .stream().forEach(e -> {
                            map.put(e.get(0, ZonedDateTime.class).getMonthValue(),
                                    e.get(1, Long.class) + map.get(e.get(0, ZonedDateTime.class).getMonthValue()));
                        });

                return map;
            }
            // today
            default -> {
                return Map.of();
            }
        }
    }

    @Override
    public Map<Integer, Long> getRevenueByMovie(StatisticTimeline timeline, Long movieId) {
        ZonedDateTime now = ZonedDateTime.now();
        Map<Integer, Long> map = new HashMap<>();

        switch (timeline) {

            // this day
            case DAY -> {
                map.put(1, 0L);

                queryFactory.selectDistinct(qInvoice.paymentDate, qInvoice.totalPrice.sum())
                        .from(qInvoice)
                        .innerJoin(qInvoice.showtime, qShowtime)
                        .innerJoin(qShowtime.movie, qMovie)
                        .where(
                                qInvoice.state.ne(ObjectState.DELETED),
                                qMovie.id.eq(movieId),
                                qInvoice.paymentStatus.eq(PaymentStatus.SUCCESS),
                                qInvoice.paymentDate.year().eq(now.getYear()),
                                qInvoice.paymentDate.month().eq(now.getMonthValue()),
                                qInvoice.paymentDate.dayOfMonth().eq(now.getDayOfMonth()))
                        .groupBy(qInvoice.paymentDate, qMovie.id)
                        .fetch()
                        .stream().forEach(e -> {
                            map.put(1, e.get(1, Long.class) + map.get(1));
                        });

                return map;
            }

            // this week
            case WEEK -> {
                for (int i = 1; i <= 7; i++) {
                    map.put(i, 0L);
                }
                queryFactory.selectDistinct(qInvoice.paymentDate, qInvoice.totalPrice.sum())
                        .from(qInvoice)
                        .innerJoin(qInvoice.showtime, qShowtime)
                        .innerJoin(qShowtime.movie, qMovie)
                        .where(
                                qInvoice.state.ne(ObjectState.DELETED),
                                qMovie.id.eq(movieId),
                                qInvoice.paymentStatus.eq(PaymentStatus.SUCCESS),
                                qInvoice.paymentDate.year().eq(now.getYear()),
                                qInvoice.paymentDate.week().eq(now.get(ChronoField.ALIGNED_WEEK_OF_YEAR) - 1))
                        .groupBy(qInvoice.paymentDate, qMovie.id)
                        .fetch()
                        .stream().forEach(e -> {
                            System.out.println(e.get(1, Long.class));
                            map.put(e.get(0, ZonedDateTime.class).getDayOfWeek().getValue(),
                                    e.get(1, Long.class) + map.get(e.get(0, ZonedDateTime.class).getDayOfWeek().getValue()));
                        });

                return map;
            }
            // this month
            case MONTH -> {
                for (int i = 1; i <= now.getMonth().length(false); i++) {
                    map.put(i, 0L);
                }

                queryFactory.selectDistinct(qInvoice.paymentDate, qInvoice.totalPrice.sum())
                        .from(qInvoice)
                        .innerJoin(qInvoice.showtime, qShowtime)
                        .innerJoin(qShowtime.movie, qMovie)
                        .where(
                                qInvoice.state.ne(ObjectState.DELETED),
                                qMovie.id.eq(movieId),
                                qInvoice.paymentStatus.eq(PaymentStatus.SUCCESS),
                                qInvoice.paymentDate.year().eq(now.getYear()),
                                qInvoice.paymentDate.month().eq(now.getMonthValue()))
                        .groupBy(qInvoice.paymentDate, qMovie.id)
                        .fetch()
                        .stream().forEach(e -> {
                            map.put(e.get(0, ZonedDateTime.class).getDayOfMonth(),
                                    e.get(1, Long.class) + map.get(e.get(0, ZonedDateTime.class).getDayOfMonth()));
                        });
                return map;
            }
//            // this year
            case YEAR -> {
                for (int i = 1; i <= 12; i++) {
                    map.put(i, 0L);
                }

                queryFactory.selectDistinct(qInvoice.paymentDate, qInvoice.totalPrice.sum())
                        .from(qInvoice)
                        .innerJoin(qInvoice.showtime, qShowtime)
                        .innerJoin(qShowtime.movie, qMovie)
                        .where(
                                qInvoice.state.ne(ObjectState.DELETED),
                                qMovie.id.eq(movieId),
                                qInvoice.paymentStatus.eq(PaymentStatus.SUCCESS),
                                qInvoice.paymentDate.year().eq(now.getYear()))
                        .groupBy(qInvoice.paymentDate, qMovie.id)
                        .fetch()
                        .stream().forEach(e -> {
                            map.put(e.get(0, ZonedDateTime.class).getMonthValue(),
                                    e.get(1, Long.class) + map.get(e.get(0, ZonedDateTime.class).getMonthValue()));
                        });

                return map;
            }
            // today
            default -> {
                return Map.of();
            }
        }
    }

    @Override
    public Map<Integer, Long> getRevenueByBranch(StatisticTimeline timeline, Long branchId) {
        ZonedDateTime now = ZonedDateTime.now();
        Map<Integer, Long> map = new HashMap<>();

        switch (timeline) {

            // this day
            case DAY -> {
                map.put(1, 0L);

                queryFactory.selectDistinct(qInvoice.paymentDate, qInvoice.totalPrice.sum())
                        .from(qInvoice)
                        .innerJoin(qInvoice.showtime, qShowtime)
                        .innerJoin(qShowtime.room, qRoom)
                        .innerJoin(qRoom.branch, qBranch)
                        .where(
                                qInvoice.state.ne(ObjectState.DELETED),
                                qBranch.id.eq(branchId),
                                qInvoice.paymentStatus.eq(PaymentStatus.SUCCESS),
                                qInvoice.paymentDate.year().eq(now.getYear()),
                                qInvoice.paymentDate.month().eq(now.getMonthValue()),
                                qInvoice.paymentDate.dayOfMonth().eq(now.getDayOfMonth()))
                        .groupBy(qInvoice.paymentDate, qBranch.id)
                        .fetch()
                        .stream().forEach(e -> {
                            map.put(1, e.get(1, Long.class) + map.get(1));
                        });

                return map;
            }

            // this week
            case WEEK -> {
                for (int i = 1; i <= 7; i++) {
                    map.put(i, 0L);
                }
                queryFactory.selectDistinct(qInvoice.paymentDate, qInvoice.totalPrice.sum())
                        .from(qInvoice)
                        .innerJoin(qInvoice.showtime, qShowtime)
                        .innerJoin(qShowtime.room, qRoom)
                        .innerJoin(qRoom.branch, qBranch)
                        .where(
                                qInvoice.state.ne(ObjectState.DELETED),
                                qBranch.id.eq(branchId),
                                qInvoice.paymentStatus.eq(PaymentStatus.SUCCESS),
                                qInvoice.paymentDate.year().eq(now.getYear()),
                                qInvoice.paymentDate.week().eq(now.get(ChronoField.ALIGNED_WEEK_OF_YEAR) - 1))
                        .groupBy(qInvoice.paymentDate, qBranch.id)
                        .fetch()
                        .stream().forEach(e -> {
                            System.out.println(e.get(1, Long.class));
                            map.put(e.get(0, ZonedDateTime.class).getDayOfWeek().getValue(),
                                    e.get(1, Long.class) + map.get(e.get(0, ZonedDateTime.class).getDayOfWeek().getValue()));
                        });

                return map;
            }
            // this month
            case MONTH -> {
                for (int i = 1; i <= now.getMonth().length(false); i++) {
                    map.put(i, 0L);
                }

                queryFactory.selectDistinct(qInvoice.paymentDate, qInvoice.totalPrice.sum())
                        .from(qInvoice)
                        .innerJoin(qInvoice.showtime, qShowtime)
                        .innerJoin(qShowtime.room, qRoom)
                        .innerJoin(qRoom.branch, qBranch)
                        .where(
                                qInvoice.state.ne(ObjectState.DELETED),
                                qBranch.id.eq(branchId),
                                qInvoice.paymentStatus.eq(PaymentStatus.SUCCESS),
                                qInvoice.paymentDate.year().eq(now.getYear()),
                                qInvoice.paymentDate.month().eq(now.getMonthValue()))
                        .groupBy(qInvoice.paymentDate, qBranch.id)
                        .fetch()
                        .stream().forEach(e -> {
                            map.put(e.get(0, ZonedDateTime.class).getDayOfMonth(),
                                    e.get(1, Long.class) + map.get(e.get(0, ZonedDateTime.class).getDayOfMonth()));
                        });
                return map;
            }

            // this year
            case YEAR -> {
                for (int i = 1; i <= 12; i++) {
                    map.put(i, 0L);
                }

                queryFactory.selectDistinct(qInvoice.paymentDate, qInvoice.totalPrice.sum())
                        .from(qInvoice)
                        .innerJoin(qInvoice.showtime, qShowtime)
                        .innerJoin(qShowtime.room, qRoom)
                        .innerJoin(qRoom.branch, qBranch)
                        .where(
                                qInvoice.state.ne(ObjectState.DELETED),
                                qBranch.id.eq(branchId),
                                qInvoice.paymentStatus.eq(PaymentStatus.SUCCESS),
                                qInvoice.paymentDate.year().eq(now.getYear()))
                        .groupBy(qInvoice.paymentDate, qBranch.id)
                        .fetch()
                        .stream().forEach(e -> {
                            map.put(e.get(0, ZonedDateTime.class).getMonthValue(),
                                    e.get(1, Long.class) + map.get(e.get(0, ZonedDateTime.class).getMonthValue()));
                        });

                return map;
            }
            // today
            default -> {
                return Map.of();
            }
        }
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

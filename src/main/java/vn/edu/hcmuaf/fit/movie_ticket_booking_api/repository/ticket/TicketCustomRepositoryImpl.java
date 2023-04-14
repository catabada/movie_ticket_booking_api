package vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.ticket;

import com.querydsl.core.BooleanBuilder;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.ticket.TicketSearch;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.QTicket;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Ticket;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.AbstractCustomRepository;

import java.util.List;

@Repository
public class TicketCustomRepositoryImpl extends AbstractCustomRepository<Ticket, Long> implements TicketCustomRepository {
    private final QTicket qTicket = QTicket.ticket;

    protected TicketCustomRepositoryImpl(EntityManager entityManager) {
        super(Ticket.class, entityManager);
    }

    @Override
    public void saveAll() {

    }

    @Override
    public List<Ticket> searchTicket(TicketSearch ticketSearch) {
        BooleanBuilder builder = buildConditionSearch(ticketSearch);
        return queryFactory.selectFrom(qTicket)
                .where(builder)
                .fetch();
    }

    private BooleanBuilder buildConditionSearch(TicketSearch ticketSearch) {
        BooleanBuilder builder = new BooleanBuilder();
        if (!ObjectUtils.isEmpty(ticketSearch.getCode())) {
            builder.and(qTicket.code.eq(ticketSearch.getCode()));
        }
        return builder;
    }
}

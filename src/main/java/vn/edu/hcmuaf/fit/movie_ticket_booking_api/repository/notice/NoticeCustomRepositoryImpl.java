package vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.notice;

import com.querydsl.core.BooleanBuilder;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.notice.NoticeSearch;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Notice;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.QNotice;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.auth.QAppUser;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.AbstractCustomRepository;

import java.util.List;

@Repository
public class NoticeCustomRepositoryImpl extends AbstractCustomRepository<Notice, Long> implements NoticeCustomRepository {
    static final QNotice qNotice = QNotice.notice;
    static final QAppUser qAppUser = QAppUser.appUser;

    protected NoticeCustomRepositoryImpl(EntityManager entityManager) {
        super(Notice.class, entityManager);
    }

    @Override
    public void saveAll() {

    }

    @Override
    public List<Notice> getNoticesSearch(NoticeSearch search) {
        return queryFactory.selectFrom(qNotice)
                .innerJoin(qNotice.appUser, qAppUser)
                .where(buildCondition(search))
                .orderBy(qNotice.createdDate.asc())
                .fetch();
    }

    BooleanBuilder buildCondition(NoticeSearch search) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (!ObjectUtils.isEmpty(search.getEmail()))
            booleanBuilder.and(qNotice.appUser.email.eq(search.getEmail()));

        return booleanBuilder;
    }
}

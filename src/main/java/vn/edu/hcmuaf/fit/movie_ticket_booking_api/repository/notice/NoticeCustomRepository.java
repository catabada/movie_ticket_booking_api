package vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.notice;

import org.springframework.data.repository.NoRepositoryBean;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.notice.NoticeSearch;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Notice;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.ICustomRepository;

import java.util.List;

@NoRepositoryBean
public interface NoticeCustomRepository extends ICustomRepository<Notice, Long> {
    List<Notice> getNoticesSearch(NoticeSearch search);
}

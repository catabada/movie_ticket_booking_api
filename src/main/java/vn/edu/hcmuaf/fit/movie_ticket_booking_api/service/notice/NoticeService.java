package vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.notice;

import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.notice.NoticeDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.notice.NoticeSearch;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.BadRequestException;

import java.util.List;

public interface NoticeService {
    NoticeDto createNotice(NoticeDto noticeDto) throws BadRequestException;

    List<NoticeDto> getAllNotice(NoticeSearch noticeSearch);
    List<NoticeDto> readAllNotice(NoticeSearch noticeSearch);
}

package vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.notice.NoticeDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.notice.NoticeSearch;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Notice;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.auth.AppUser;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.BadRequestException;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper.AppUserMapper;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper.NoticeMapper;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.app_user.AppUserCustomRepository;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.notice.NoticeCustomRepository;

import java.util.List;
import java.util.Optional;

@Service
public class NoticeServiceImpl implements NoticeService {
    private NoticeCustomRepository noticeCustomRepository;
    private AppUserCustomRepository appUserCustomRepository;
    private NoticeMapper noticeMapper;
    private AppUserMapper appUserMapper;

    @Autowired
    public NoticeServiceImpl(NoticeCustomRepository noticeCustomRepository, AppUserCustomRepository appUserCustomRepository, NoticeMapper noticeMapper, AppUserMapper appUserMapper) {
        this.noticeCustomRepository = noticeCustomRepository;
        this.appUserCustomRepository = appUserCustomRepository;
        this.noticeMapper = noticeMapper;
        this.appUserMapper = appUserMapper;
    }

    @Override
    @Transactional
    public NoticeDto createNotice(NoticeDto noticeDto) throws BadRequestException {
        Optional<AppUser> optional = appUserCustomRepository.getUserByEmail(noticeDto.getAppUser().getEmail());
        if (optional.isPresent()) {
            noticeDto.setAppUser(appUserMapper.toAppUserDtoShowNotices(optional.get()));
        } else {
            throw new BadRequestException("Không tìm thấy người dùng");
        }
        Notice notice = noticeCustomRepository.saveAndFlush(noticeMapper.toNotice(noticeDto));

        if (ObjectUtils.isEmpty(notice)) throw new BadRequestException("Tạo thông báo thất bại");

        return noticeDto;
    }

    @Override
    public List<NoticeDto> getAllNotice(NoticeSearch noticeSearch) {
        return noticeMapper.toNoticeDtos(noticeCustomRepository.getNoticesSearch(noticeSearch));
    }

    @Override
    @Transactional
    public List<NoticeDto> readAllNotice(NoticeSearch noticeSearch) {
        List<Notice> notices = noticeCustomRepository.getNoticesSearch(noticeSearch);
        notices = notices.stream().peek(notice -> notice.setIsRead(true)).toList();
        return noticeMapper.toNoticeDtos(noticeCustomRepository.saveAllAndFlush(notices));
    }
}

package vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.notice.NoticeDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Notice;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AppUserMapper.class})
public interface NoticeMapper {
    @Mapping(target = "appUser.password", ignore = true)
    @Mapping(target = "appUser.phone", ignore = true)
    @Mapping(target = "appUser.facebookId", ignore = true)
    @Mapping(target = "appUser.googleId", ignore = true)
    @Mapping(target = "appUser", source = "appUser", qualifiedByName = "toAppUserDtoWithoutInvoicesAndVerificationTokens")
    NoticeDto toNoticeDto(Notice notice);

    Notice toNotice(NoticeDto noticeDto);

    List<NoticeDto> toNoticeDtos(List<Notice> notices);

    List<Notice> toNotices(List<NoticeDto> noticeDtos);
}

package vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper;

import org.mapstruct.Mapper;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.user_info.UserInfoDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.auth.UserInfo;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserInfoMapper {
    UserInfoDto toUserInfoDto(UserInfo userInfo);

    UserInfo toUserInfo(UserInfoDto userInfoDto);

    List<UserInfo> toUserInfoList(List<UserInfoDto> userInfoDtoList);

    List<UserInfoDto> toUserInfoDtoList(List<UserInfo> userInfoList);
}

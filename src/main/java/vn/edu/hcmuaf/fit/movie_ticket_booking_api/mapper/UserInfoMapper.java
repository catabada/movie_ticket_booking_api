package vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.user_info.UserInfoDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.auth.AppUser;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.auth.UserInfo;

import java.util.List;

@Mapper(componentModel = "spring", uses = {InvoiceMapper.class})
public interface UserInfoMapper {
    @Mapping(target = "fullName", source = "appUser.userInfo.fullName")
    @Mapping(target = "isMale", source = "appUser.userInfo.isMale")
    @Mapping(target = "avatar", source = "appUser.userInfo.avatar")
    @Mapping(target = "dateOfBirth", source = "appUser.userInfo.dateOfBirth")
    @Mapping(target = "invoices", source = "appUser.invoices", qualifiedByName = "toInvoiceDtoWithoutAppUser")
    UserInfoDto toUserInfoDto(AppUser appUser);

    UserInfo toUserInfo(UserInfoDto userInfoDto);

    List<UserInfo> toUserInfoList(List<UserInfoDto> userInfoDtoList);

    List<UserInfoDto> toUserInfoDtoList(List<UserInfo> userInfoList);
}

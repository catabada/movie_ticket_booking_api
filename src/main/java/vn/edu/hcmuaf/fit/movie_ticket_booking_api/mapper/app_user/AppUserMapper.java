package vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper.app_user;

import org.mapstruct.Mapper;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.app_user.AppUserDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.auth.AppUser;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppUserMapper {
    AppUserDto toAppUserDto(AppUser appUser);

    AppUser toAppUser(AppUserDto appUserDto);

    List<AppUser> toAppUserList(List<AppUserDto> appUserDtoList);

    List<AppUserDto> toAppUserDtoList(List<AppUser> appUserList);
}

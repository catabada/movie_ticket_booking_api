package vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.app_user;

import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.app_user.AppUserDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.app_user.UserRegisterDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.auth.AppUser;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.BadRequestException;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.BaseException;

public interface AppUserService {
    AppUserDto register(final AppUserDto appUserDto) throws BaseException;
}

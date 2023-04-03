package vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.app_user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.app_user.AppUserDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.app_user.UserRegisterDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.auth.AppUser;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.BadRequestException;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.BaseException;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper.app_user.AppUserMapper;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.app_user.AppUserCustomRepository;

@Service
public class AppUserServiceImpl implements AppUserService {
    private final AppUserCustomRepository appUserCustomRepository;
    private final AppUserMapper appUserMapper;

    @Autowired
    public AppUserServiceImpl(AppUserCustomRepository appUserCustomRepository, AppUserMapper appUserMapper) {
        this.appUserCustomRepository = appUserCustomRepository;
        this.appUserMapper = appUserMapper;
    }

    @Override
    public AppUserDto register(AppUserDto dto) throws BaseException {
        AppUser appUser = appUserCustomRepository.save(appUserMapper.toAppUser(dto));
        if(ObjectUtils.isEmpty(appUser)) {
            throw new BadRequestException("Register failed");
        }
        return appUserMapper.toAppUserDto(appUser);
    }
}

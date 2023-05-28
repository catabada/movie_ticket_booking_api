package vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.app_user.AppUserDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.invoice.InvoiceDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Invoice;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.auth.AppUser;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AppUserMapper {
    @Named("toAppUserDtoWithoutAppRolesAndVerificationTokens")
    @Mapping(target = "verificationTokens", ignore = true)
    @Mapping(target = "appRoles", ignore = true)
    AppUserDto toAppUserDtoWithoutAppRolesAndVerificationTokens(AppUser appUser);

    @Named("toAppUserDtoWithoutInvoicesAndVerificationTokens")
    @Mapping(target = "verificationTokens", ignore = true)
    @Mapping(target = "invoices", ignore = true)
    AppUserDto toAppUserDtoWithoutInvoices(AppUser appUser);

    @Mapping(target = "appRoles", ignore = true)
    @Mapping(target = "verificationTokens", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "googleId", ignore = true)
    @Mapping(target = "facebookId", ignore = true)
    AppUserDto toAppUserDtoShowNotices(AppUser appUser);

    AppUser toAppUser(AppUserDto appUserDto);

    List<AppUser> toAppUserList(List<AppUserDto> appUserDtoList);

    @IterableMapping(qualifiedByName = "toAppUserDtoWithoutInvoicesAndVerificationTokens")
    List<AppUserDto> toAppUserDtoList(List<AppUser> appUserList);

}

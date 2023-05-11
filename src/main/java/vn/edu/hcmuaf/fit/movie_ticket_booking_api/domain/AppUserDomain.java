package vn.edu.hcmuaf.fit.movie_ticket_booking_api.domain;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.BeanIdConstant;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.auth.AppUser;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.infrastructure.AppJwtTokenProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@NoArgsConstructor

public class AppUserDomain implements UserDetails {
    private AppUser appUser;

    public AppUserDomain(AppUser appUser) {
        this.appUser = appUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        appUser.getAppRoles().forEach(role -> {
            grantedAuthorityList.add(new SimpleGrantedAuthority(role.getName()));
        });
        return grantedAuthorityList;
    }

    @Override
    public String getPassword() {
        return appUser.getPassword();
    }

    @Override
    public String getUsername() {
        return appUser.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return appUser.getAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public String getEmail() {
        return appUser.getEmail();
    }

    @Override
    public boolean isEnabled() {
        return appUser.getEnabled();
    }

    public Long getUserId() {
        return this.appUser.getId();
    }

    public String getFullName() {
        return this.appUser.getUserInfo().getFullName();
    }

    public String getAvatar() {
        return this.appUser.getUserInfo().getAvatar();
    }
}

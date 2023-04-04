package vn.edu.hcmuaf.fit.movie_ticket_booking_api.domain;

import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.auth.AppUser;

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
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return appUser.getAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    public String getEmail() {
        return appUser.getEmail();
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public Long getUserId() {
        return this.appUser.getId();
    }

    public String getFirstName() {
        return this.appUser.getUserInfo().getFirstName();
    }

    public String getAvatar() {
        return this.appUser.getUserInfo().getAvatar();
    }
}

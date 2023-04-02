package vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.auth;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.BaseObject;

import java.io.Serializable;

@Entity
@Table(name = "app_user")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class AppUser extends BaseObject implements Serializable {
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "phone", nullable = false, unique = true)
    private String phone;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "full_name", nullable = false)
    private String fullName;
    @Column(name="enabled")
    private Boolean enabled;
    @Column(name="accountNonLocked")
    private Boolean accountNonLocked;
    @Column(name="facebook_id")
    private String facebookId;
    @Column(name="google_id")
    private String googleId;
    @OneToOne(mappedBy = "appUser", cascade = CascadeType.ALL)
    @JoinColumn(name = "user_info_id", referencedColumnName = "id")
    private UserInfo userInfo;


}

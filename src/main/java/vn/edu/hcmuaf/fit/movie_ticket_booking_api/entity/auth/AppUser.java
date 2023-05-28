package vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.BaseObject;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Invoice;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Notice;

import java.util.Set;

@Entity
@Table(name = "app_user")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class AppUser extends BaseObject {
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "phone", nullable = false, unique = true)
    private String phone;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name="enabled")
    private Boolean enabled;

    @Column(name="account_non_locked")
    private Boolean accountNonLocked;

    @Column(name="facebook_id")
    private String facebookId;

    @Column(name="google_id")
    private String googleId;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<AppRole> appRoles;

    @OneToOne(mappedBy = "appUser", cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id")
    private UserInfo userInfo;

    @JsonIgnoreProperties("appUser")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "appUser")
    private Set<VerificationToken> verificationTokens;

    @JsonIgnoreProperties("appUser")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "appUser")
    private Set<Invoice> invoices;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "appUser")
    private Set<Notice> notices;
}

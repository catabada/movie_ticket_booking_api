package vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.auth;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.BaseObject;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "user_info")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class UserInfo extends BaseObject implements Serializable {
    @Transient
    private String fullName;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "is_male")
    private Boolean isMale;
    @Column(name = "avatar")
    private String avatar;
    @Column(name = "date_of_birth")
    private Date dateOfBirth;
    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private AppUser appUser;

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

}

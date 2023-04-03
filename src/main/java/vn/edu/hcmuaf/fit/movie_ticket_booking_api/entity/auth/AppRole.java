package vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.auth;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.BaseObject;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "app_role")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class AppRole extends BaseObject implements Serializable {
    @Column(name = "name", nullable = false, unique = true)
    private String name;

//    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "appRoles")
//    private List<AppUser> appUsers;
}

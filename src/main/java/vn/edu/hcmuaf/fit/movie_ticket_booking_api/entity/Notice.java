package vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.NoticeType;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.auth.AppUser;

@Entity
@Table(name = "notice")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Notice extends BaseObject {
    private String description;
    private NoticeType type;

    @Column(name = "is_read")
    private Boolean isRead;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

}

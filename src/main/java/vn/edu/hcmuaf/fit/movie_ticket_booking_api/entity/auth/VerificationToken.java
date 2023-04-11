package vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.auth;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.BaseObject;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(name = "verification_token")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class VerificationToken extends BaseObject implements Serializable {
    @Column(name = "token")
    @GeneratedValue(generator = "UUID")
    private UUID token;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser appUser;

    @Column(name="is_sent")
    private Boolean isSent;

    @Column(name = "last_sent")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private ZonedDateTime lastSent;

    @Column(name = "is_verified")
    private Boolean isVerified;

    @Column(name = "verified_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private ZonedDateTime verifiedDate;

    @Column(name = "expired_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private ZonedDateTime expiredDate;
}

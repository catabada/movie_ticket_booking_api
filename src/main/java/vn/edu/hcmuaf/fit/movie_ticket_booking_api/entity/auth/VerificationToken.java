package vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.auth;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.BaseObject;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "verification_token")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class VerificationToken extends BaseObject implements Serializable {
    @Column(name = "token")
//    @Type(type = "uuid-char")
    private UUID token;
}

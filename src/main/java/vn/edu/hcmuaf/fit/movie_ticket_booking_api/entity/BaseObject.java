package vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.ObjectState;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@MappedSuperclass
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
public class BaseObject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    protected ObjectState state;
    @Column(name = "created_date", nullable = false)
    @CurrentTimestamp
    protected ZonedDateTime createdDate;
    @Column(name = "modified_date")
    @UpdateTimestamp
    protected ZonedDateTime modifiedDate;
    @Column(name = "deleted_date")
    protected ZonedDateTime deletedDate;
}

package vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.ObjectState;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@MappedSuperclass
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseObject implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Column(name = "state")
    @Enumerated(EnumType.STRING)
    protected ObjectState state;
    @Column(name = "created_date", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CurrentTimestamp
    protected ZonedDateTime createdDate;
    @Column(name = "modified_date", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @UpdateTimestamp
    protected ZonedDateTime modifiedDate;
    @Column(name = "deleted_date")
    protected ZonedDateTime deletedDate;
}

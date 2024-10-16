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

@MappedSuperclass
@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
public class BaseObject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    @Column(name = "state", nullable = false, columnDefinition = "varchar(20) default 'ACTIVE'")
    @Enumerated(EnumType.STRING)
    protected ObjectState state;
    @Column(name = "created_date", nullable = false)
    @CurrentTimestamp
    protected LocalDateTime createdDate;
    @Column(name = "modified_date")
    @UpdateTimestamp
    protected LocalDateTime modifiedDate;
    @Column(name = "deleted_date")
    protected LocalDateTime deletedDate;
}

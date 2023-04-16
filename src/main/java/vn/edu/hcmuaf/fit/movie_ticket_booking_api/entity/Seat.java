package vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.SeatType;

@Entity
@Table(name = "seat")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Seat extends BaseObject {
    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private int horizontal;

    @Column(nullable = false)
    private int vertical;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "room_id")
    private Room room;
}

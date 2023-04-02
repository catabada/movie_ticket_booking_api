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
    @Column(name = "code")
    private String code;

    @Column(name = "horizontal_index")
    private int horizontalIndex;

    @Column(name = "vertical_index")
    private int verticalIndex;

    @Column(name = "type")
    private SeatType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;
}

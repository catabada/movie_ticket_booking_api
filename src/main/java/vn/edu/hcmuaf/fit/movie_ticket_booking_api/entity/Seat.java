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

    @Column(name = "is_seat")
    private Boolean isSeat;

    @Column(name = "row_index")
    private int rowIndex;

    @Column(name = "row")
    private int row;

    @Column(name = "col")
    private int col;

    @Column(name = "column_index")
    private int columnIndex;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;
}

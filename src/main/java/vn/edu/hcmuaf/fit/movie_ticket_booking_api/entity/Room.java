package vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.RoomState;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.RoomType;

import java.util.*;

@Entity
@Table(name = "room")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Room extends BaseObject {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "row", nullable = false)
    private int row;
    @Column(name = "col", nullable = false)
    private int col;
    @Column(name = "total_seat", nullable = false)
    private int totalSeat;
    @Column(name = "room_state")
    private RoomState roomState;
    @Column(name = "room_type")
    private RoomType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Seat> seats = new ArrayList<>();
}

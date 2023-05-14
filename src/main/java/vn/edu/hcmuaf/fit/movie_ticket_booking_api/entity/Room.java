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
    @Column(nullable = false)
    private String name;

    @Column(name = "row", nullable = false)
    private int row;

    @Column(name = "col", nullable = false)
    private int col;

    @Column(name = "lane_row")
    @ElementCollection(targetClass = Integer.class)
    private List<Integer> laneRows;


    @Column(name = "lane_col")
    @ElementCollection(targetClass = Integer.class)
    private List<Integer> laneCols;

    @Column(name = "total_seat", nullable = false)
    private int totalSeat;

    @Column(name = "room_state", nullable = false)
    @Enumerated(EnumType.STRING)
    private RoomState roomState;

    @Column(name = "room_type")
    @Enumerated(EnumType.STRING)
    private RoomType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private Set<Seat> seats = new HashSet<>();

    public void addSeat(Seat seat) {
        if (seats == null) seats = new HashSet<>();

        seats.add(seat);
        seat.setRoom(this);
    }
}

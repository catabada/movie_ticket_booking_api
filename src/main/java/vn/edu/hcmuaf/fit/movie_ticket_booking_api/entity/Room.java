package vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.RoomState;

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

    @Column(name="room_state", nullable = false)
    @Enumerated(EnumType.STRING)
    private RoomState roomState;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Seat> seats = new ArrayList<>();
}

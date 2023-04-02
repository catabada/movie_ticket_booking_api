package vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.BranchStatus;

import java.util.*;

@Entity
@Table(name = "branch")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Branch extends BaseObject {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private BranchStatus status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "branch")
    private List<Room> rooms = new ArrayList<>();
}

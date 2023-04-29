package vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.BranchStatus;

import java.util.*;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Branch extends BaseObject {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String address;

    @Column(name = "branch_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private BranchStatus branchStatus;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "branch")
    private List<Room> rooms = new ArrayList<>();
}

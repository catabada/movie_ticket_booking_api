package vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.room;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.branch.BranchDto;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class RoomSearch {
    BranchDto branch;
}

package vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.branch;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class BranchSearch {
    private boolean hasRoom;
}

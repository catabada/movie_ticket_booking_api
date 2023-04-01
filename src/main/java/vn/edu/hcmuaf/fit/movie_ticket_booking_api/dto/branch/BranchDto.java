package vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.branch;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.BranchStatus;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.object_key.BaseObjectDto;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class BranchDto extends BaseObjectDto {
    @NotNull
    protected String name;

    @NotNull
    protected String address;

    protected BranchStatus status;
}

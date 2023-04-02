package vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.branch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.BranchStatus;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.object_key.BaseObjectDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.room.RoomDto;

import java.util.*;

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

    @JsonIgnoreProperties("branch")
    protected List<RoomDto> rooms = new ArrayList<>();
}

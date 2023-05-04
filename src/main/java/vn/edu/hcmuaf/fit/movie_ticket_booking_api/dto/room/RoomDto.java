package vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.room;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.RoomState;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.RoomType;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.branch.BranchDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.object_key.BaseObjectDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.seat.SeatDto;

import java.util.*;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class RoomDto extends BaseObjectDto {
    @NotNull
    private String name;
    @NotNull
    private int row;
    @NotNull
    private int col;
    @NotNull
    private int totalSeat;
    private RoomState roomState;
    private RoomType type;

    @NotNull
    @JsonIgnoreProperties("rooms")
    private BranchDto branch;

    @JsonIgnoreProperties("room")
    private List<SeatDto> seats = new ArrayList<>();
}

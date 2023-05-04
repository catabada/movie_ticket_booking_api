package vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.seat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.SeatType;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.object_key.BaseObjectDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.room.RoomDto;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class SeatDto extends BaseObjectDto {
    @NotNull
    private String code;
    @NotNull
    private Boolean isSeat;
    @NotNull
    private int columnIndex;
    private int col;

    @NotNull
    private int rowIndex;
    private int row;

    @NotNull
    private SeatType type;

    @JsonIgnoreProperties("seats")
    private RoomDto room;
}

package vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.showtime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.invoice.InvoiceDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.MovieFormat;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.movie.MovieDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.object_key.BaseObjectDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.room.RoomDto;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class ShowtimeDto extends BaseObjectDto {

    @NotNull
    private MovieDto movie;

    @NotNull
    private RoomDto room;

    @NotNull
    @Min(value = 1000, message = "Giá vé phải lớn hơn 1000 đồng")
    private Long price;

    @NotNull
    private MovieFormat movieFormat;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private ZonedDateTime startTime;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private ZonedDateTime endTime;

    @JsonIgnoreProperties("showtime")
    List<InvoiceDto> invoices = new ArrayList<>();
}

package vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.invoice;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.app_user.AppUserDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.object_key.BaseObjectDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.showtime.ShowtimeDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.ticket.TicketDto;

import java.time.ZonedDateTime;
import java.util.*;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class InvoiceDto extends BaseObjectDto {
    private String code;

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    private AppUserDto appUser;

    @NotNull
    private ShowtimeDto showtime;

    private String paymentMethod;

    private String paymentStatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private ZonedDateTime paymentDate;

    @JsonIgnoreProperties("invoice")
    List<TicketDto> tickets = new ArrayList<>();

    public void addTicket(TicketDto ticket) {
        if (tickets == null) {
            tickets = new ArrayList<>();
        }

        ticket.setInvoice(this);
        tickets.add(ticket);
    }
}

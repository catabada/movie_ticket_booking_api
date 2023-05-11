package vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.invoice;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.PaymentMethod;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.PaymentStatus;
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

    @NotBlank(message = "Tên không được để trống")
    private String name;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không hợp lệ")
    private String email;

    @NotBlank(message = "Tổng tiền không được để trống")
    private Long totalPrice;

    private AppUserDto appUser;

    @NotNull(message = "Suất chiếu không hợp lệ")
    private ShowtimeDto showtime;

    @NotNull(message = "Phương thức thanh toán không hợp lệ")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    private ZonedDateTime paymentDate;

    @JsonIgnoreProperties("invoice")
    List<TicketDto> tickets = new ArrayList<>();

    @JsonIgnoreProperties("invoice")
    List<InvoiceComboDto> invoiceCombos = new ArrayList<>();

    public void addTicket(TicketDto ticket) {
        if (tickets == null) {
            tickets = new ArrayList<>();
        }

        ticket.setInvoice(this);
        tickets.add(ticket);
    }
}

package vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.PaymentMethod;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.PaymentStatus;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.auth.AppUser;

import java.time.ZonedDateTime;
import java.util.*;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Invoice extends BaseObject {

    @Column(updatable = false, nullable = false)
    private String code;

    @Column(updatable = false, nullable = false)
    private String name;

    @Column(updatable = false, nullable = false)
    private String email;

    @Column(name = "total_price")
    private Long totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "showtime_id")
    private Showtime showtime;

    @Column(name = "payment_method")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Column(name = "payment_status")
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    @CreationTimestamp
    private ZonedDateTime paymentDate;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    List<Ticket> tickets = new ArrayList<>();

    public void addTicket(Ticket ticket) {
        if (tickets == null) {
            tickets = new ArrayList<>();
        }

        ticket.setInvoice(this);
        tickets.add(ticket);
    }
}

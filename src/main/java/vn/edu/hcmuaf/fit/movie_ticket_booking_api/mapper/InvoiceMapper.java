package vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper;

import org.mapstruct.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.invoice.InvoiceDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.ticket.TicketDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Invoice;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Ticket;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {ShowtimeMapper.class, TicketMapper.class}
)
public interface InvoiceMapper {
    @Named("toInvoiceDto")
    @Mapping(target = "showtime", source = "showtime", qualifiedByName = "toShowtimeDtoWithoutInvoices")
    @Mapping(target = "tickets", source = "tickets", qualifiedByName = "toTicketDto")
    InvoiceDto toInvoiceDto(Invoice userInvoice);

    Invoice toInvoice(InvoiceDto invoiceDto);

    @IterableMapping(qualifiedByName = "toInvoiceDto")
    List<InvoiceDto> toInvoiceDtoList(List<Invoice> invoices);

    List<Invoice> toInvoiceList(List<InvoiceDto> invoiceDtos);

}

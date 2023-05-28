package vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.invoice;

import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.invoice.InvoiceDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.invoice.InvoiceSearch;

import java.util.List;

public interface InvoiceService {
    List<InvoiceDto> searchInvoice(final InvoiceSearch search);
}

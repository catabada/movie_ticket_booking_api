package vn.edu.hcmuaf.fit.movie_ticket_booking_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.ticket.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.handler.response.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.ticket.TicketService;

import java.util.List;

@RestController
@RequestMapping("/ticket")
public class TicketController {
    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @RequestMapping("/search")
    public ResponseEntity<HttpResponse> searchTicket(@RequestBody TicketSearch ticketSearch) throws Exception {
        List<TicketDto> ticketDtoList = ticketService.searchTicket(ticketSearch);
        return ResponseEntity.ok(HttpResponseSuccess.success(ticketDtoList).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HttpResponse> getTicketInfo(@PathVariable("id") Long id) throws Exception {
        TicketInfoDto ticketInfoDto = ticketService.getTicketInfo(id);
        return ResponseEntity.ok(HttpResponseSuccess.success(ticketInfoDto).build());
    }

    @PostMapping("/booking")
    public ResponseEntity<HttpResponse> bookingTicket(@RequestBody TicketCreate ticketCreate) throws Exception {
        TicketDto ticketDto = ticketService.bookingTicket(ticketCreate);
        return ResponseEntity.ok(HttpResponseSuccess.success(ticketDto).build());
    }

    @PutMapping
    public ResponseEntity<HttpResponse> updateTicket(@RequestBody TicketUpdate ticketUpdate) throws Exception {
        TicketDto ticketDto = ticketService.updateTicket(ticketUpdate);
        return ResponseEntity.ok(HttpResponseSuccess.success(ticketDto).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpResponse> deleteTicket(@PathVariable("id") Long id) throws Exception {
        ticketService.deleteTicket(id);
        return ResponseEntity.ok(HttpResponseSuccess.success("Delete ticket successfully").build());
    }
}

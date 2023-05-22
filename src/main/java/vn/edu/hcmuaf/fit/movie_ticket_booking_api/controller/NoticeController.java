package vn.edu.hcmuaf.fit.movie_ticket_booking_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.notice.NoticeCreate;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.notice.NoticeSearch;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.BadRequestException;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.handler.response.HttpResponse;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.handler.response.HttpResponseSuccess;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.notice.NoticeService;

@Controller
@RequestMapping("/notice")
public class NoticeController {
    private NoticeService noticeService;

    @Autowired
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @RequestMapping("/search")
    public ResponseEntity<HttpResponse> getNoticesSearch(@RequestBody NoticeSearch search) {
        return ResponseEntity.ok(HttpResponseSuccess.success(noticeService.getAllNotice(search)).build());
    }
    @RequestMapping("/read")
    public ResponseEntity<HttpResponse> readAllNotice(@RequestBody NoticeSearch search) {
        return ResponseEntity.ok(HttpResponseSuccess.success(noticeService.readAllNotice(search)).build());
    }

    @PostMapping("/create")
    public ResponseEntity<HttpResponse> createNotice(@RequestBody NoticeCreate noticeCreate) throws BadRequestException {
        return ResponseEntity.ok(HttpResponseSuccess.success(noticeService.createNotice(noticeCreate.getNotice())).build());
    }


}

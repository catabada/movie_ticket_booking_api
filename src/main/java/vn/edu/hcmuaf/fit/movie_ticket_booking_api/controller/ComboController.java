package vn.edu.hcmuaf.fit.movie_ticket_booking_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.combo.ComboCreate;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.combo.ComboDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.BaseException;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.handler.response.HttpResponse;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.handler.response.HttpResponseSuccess;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.combo.ComboService;

@Controller
@RequestMapping("/combo")
public class ComboController {
    private final ComboService comboService;

    @Autowired
    public ComboController(final ComboService comboService) {
        this.comboService = comboService;
    }

    @RequestMapping("/search")
    public ResponseEntity<HttpResponse> searchCombo() throws BaseException {
        return ResponseEntity.ok(HttpResponseSuccess.success(comboService.getCombos()).build());
    }

    @PostMapping("/create")
    public ResponseEntity<HttpResponse> createCombo(@RequestBody final ComboCreate create) throws BaseException {
        ComboDto comboDto = comboService.createCombo(create.getCombo());
    return ResponseEntity.ok(HttpResponseSuccess.success(comboDto).build());
    }
}

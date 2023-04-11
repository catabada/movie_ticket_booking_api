package vn.edu.hcmuaf.fit.movie_ticket_booking_api.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.room.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.BaseException;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.handler.response.HttpResponse;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.handler.response.HttpResponseSuccess;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.room.RoomService;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {
    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping
    public ResponseEntity<HttpResponse> getAllRoom() {
        List<RoomDto> roomDtoList = roomService.getAllRoom();
        return ResponseEntity.ok(HttpResponseSuccess.success(roomDtoList).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HttpResponse> getRoomById(@PathVariable("id") Long id) throws BaseException {
        RoomDto roomDto = roomService.getRoomById(id);
        return ResponseEntity.ok(HttpResponseSuccess.success(roomDto).build());
    }

    @PostMapping
    public ResponseEntity<HttpResponse> createRoom(@RequestBody @Valid RoomCreate roomCreate) throws BaseException {
        RoomDto roomDto = roomService.createRoom(roomCreate);
        return ResponseEntity.ok(HttpResponseSuccess.success(roomDto).build());
    }

    @PutMapping
    public ResponseEntity<HttpResponse> updateRoom(@RequestBody @Valid RoomUpdate roomUpdate) throws BaseException {
        RoomDto roomDto = roomService.updateRoom(roomUpdate);
        return ResponseEntity.ok(HttpResponseSuccess.success(roomDto).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpResponse> deleteRoom(@PathVariable("id") Long id) throws BaseException {
        roomService.deleteRoom(id);
        return ResponseEntity.ok(HttpResponseSuccess.success().build());
    }
}

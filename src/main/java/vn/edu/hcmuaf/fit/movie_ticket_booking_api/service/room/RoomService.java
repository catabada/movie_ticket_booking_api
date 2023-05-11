package vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.room;

import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.room.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.BaseException;

import java.util.List;

public interface RoomService {
    List<RoomDto> getAllRoom(final RoomSearch search);
    RoomDto getRoomById(Long id) throws BaseException;
    RoomDto createRoom(RoomCreate roomCreate) throws BaseException;
    RoomDto updateRoom(RoomUpdate roomUpdate) throws BaseException;
    void deleteRoom(Long id) throws BaseException;
}

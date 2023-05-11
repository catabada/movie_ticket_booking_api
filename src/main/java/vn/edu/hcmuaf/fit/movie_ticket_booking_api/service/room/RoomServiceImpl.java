package vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.RoomState;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.room.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.*;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper.BranchMapper;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper.RoomMapper;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.branch.BranchCustomRepository;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.room.RoomCustomRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {
    private final BranchCustomRepository branchCustomRepository;
    private final RoomCustomRepository roomCustomRepository;
    private final BranchMapper branchMapper;
    private final RoomMapper roomMapper;

    @Autowired
    public RoomServiceImpl(BranchCustomRepository branchCustomRepository, RoomCustomRepository roomCustomRepository,
                           BranchMapper branchMapper, RoomMapper roomMapper) {
        this.branchCustomRepository = branchCustomRepository;
        this.roomCustomRepository = roomCustomRepository;
        this.branchMapper = branchMapper;
        this.roomMapper = roomMapper;
    }

    @Override
    public List<RoomDto> getAllRoom(RoomSearch search) {
        List<Room> rooms = roomCustomRepository.getRoomsSearch(search);
        return roomMapper.toRoomDtoList(rooms);
    }

    @Override
    public RoomDto getRoomById(Long id) throws BaseException {
        Optional<Room> room = roomCustomRepository.findById(id);
        return roomMapper.toRoomDto(room.orElseThrow(() -> new BaseException("Room not found")));
    }

    @Override
    @Transactional
    public RoomDto createRoom(RoomCreate roomCreate) throws BaseException {
        Branch branch =
                branchCustomRepository.findById(roomCreate.getBranch().getId())
                        .orElseThrow(() -> new BadRequestException("Branch not found"));
        roomCreate.setBranch(branchMapper.toBranchDto(branch));


        Room room = roomMapper.toRoom(roomCreate);
        room.setRoomState(RoomState.AVAILABLE);

        for (Seat seat : room.getSeats()) {
            seat.setRoom(room);
        }

        room.setSeats(room.getSeats());

       room = roomCustomRepository.save(room);

        return roomMapper.toRoomDto(room);
    }

    @Override
    public RoomDto updateRoom(RoomUpdate roomUpdate) throws BaseException {
        Optional<Room> room = roomCustomRepository.findById(roomUpdate.getId());

        if (room.isEmpty()) throw new NotFoundException("Room not found");

        Room updatedRoom = roomCustomRepository.save(roomMapper.toRoom(roomUpdate));
        return roomMapper.toRoomDto(updatedRoom);
    }

    @Override
    public void deleteRoom(Long id) throws BaseException {
        roomCustomRepository.deleteById(id);
    }
}

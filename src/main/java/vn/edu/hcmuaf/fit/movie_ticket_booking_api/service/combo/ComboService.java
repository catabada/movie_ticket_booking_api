package vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.combo;

import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.combo.ComboDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.combo.ComboItemDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.ComboItem;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.BaseException;

import java.util.List;

public interface ComboService {
    ComboDto getCombo(final Long id);

    List<ComboDto> getCombos() throws BaseException;

    ComboDto createCombo(final ComboDto comboDto) throws BaseException;

    ComboDto updateCombo(final ComboDto comboDto) throws BaseException;

    void deleteCombo(final Long id) throws BaseException;
}

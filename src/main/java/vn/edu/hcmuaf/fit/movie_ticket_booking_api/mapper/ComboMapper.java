package vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.combo.ComboDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Combo;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ComboItemMapper.class})
public interface ComboMapper {
    @Named("toComboDto")
    @Mapping(target = "comboItems", source = "comboItems", qualifiedByName = "toComboItemDto")
    ComboDto toComboDto(final Combo combo);

    Combo toCombo(final ComboDto comboDto);

    List<Combo> toCombos(final List<ComboDto> comboDtos);

    List<ComboDto> toComboDtos(final List<Combo> combos);
}

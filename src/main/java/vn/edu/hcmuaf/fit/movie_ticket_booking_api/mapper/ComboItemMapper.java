package vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.combo.ComboItemDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.ComboItem;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ComboItemMapper {

    ComboItem toComboItem(ComboItemDto comboItemDto);

    @Named("toComboItemDto")
    @Mapping(target = "combo", ignore = true)
    ComboItemDto toComboItemDto(ComboItem comboItem);

    List<ComboItem> toComboItems(List<ComboItemDto> comboItemDtos);

    @IterableMapping(qualifiedByName = "toComboItemDto")
    List<ComboItemDto> toComboItemDtos(List<ComboItem> comboItems);


}

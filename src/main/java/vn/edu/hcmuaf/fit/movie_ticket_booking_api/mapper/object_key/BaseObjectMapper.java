package vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper.object_key;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.object_key.BaseObjectDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.BaseObject;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BaseObjectMapper {
    BaseObjectDto toBaseObjectDto(BaseObject baseObject);

    BaseObject toBaseObject(BaseObjectDto baseObjectDto);

    List<BaseObjectDto> toBaseObjectDtoList(List<BaseObject> baseObjectList);

    List<BaseObject> toBaseObjectList(List<BaseObjectDto> baseObjectDtoList);

}

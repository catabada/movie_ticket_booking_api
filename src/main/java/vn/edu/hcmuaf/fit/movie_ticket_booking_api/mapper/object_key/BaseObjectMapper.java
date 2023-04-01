package vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper.object_key;

import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.object_key.BaseObjectDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.BaseObject;

import java.util.List;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface BaseObjectMapper {
    BaseObjectDto toObjectKeyDto(BaseObject objectKey);

    BaseObject toObjectKey(BaseObjectDto objectKeyDto);

    List<BaseObjectDto> toObjectKeyDtoList(List<BaseObject> objectKeyList);

    List<BaseObject> toObjectKeyList(List<BaseObjectDto> objectKeyDtoList);

}

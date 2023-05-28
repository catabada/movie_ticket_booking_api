package vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.combo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.object_key.BaseObjectDto;

import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class ComboDto extends BaseObjectDto {
    private String name;
    private Long price;
    private String image;
    private List<ComboItemDto> comboItems;
}

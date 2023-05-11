package vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.combo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.object_key.BaseObjectDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.product.ProductDto;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
public class ComboItemDto extends BaseObjectDto {
    private Integer quantity;
    private ProductDto product;
    private ComboDto combo;
}

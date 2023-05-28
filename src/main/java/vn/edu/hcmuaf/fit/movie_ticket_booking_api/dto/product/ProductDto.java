package vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.ProductType;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.object_key.BaseObjectDto;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class ProductDto extends BaseObjectDto {
    private String name;
    private Long price;
    private ProductType productType;
    private Integer stock;
}

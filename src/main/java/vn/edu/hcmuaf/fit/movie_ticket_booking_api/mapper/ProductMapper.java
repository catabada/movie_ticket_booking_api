package vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.product.ProductDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Product;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toProductDto(Product product);

    Product toProduct(ProductDto productDto);

    List<ProductDto> toProductDtos(List<Product> products);

    List<Product> toProducts(List<ProductDto> productDtos);
}

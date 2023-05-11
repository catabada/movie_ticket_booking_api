package vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.product;

import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.product.ProductDto;

import java.util.List;

public interface ProductService {
    List<ProductDto> getAll();

    ProductDto getByProduct(final Long id) throws Exception;

    ProductDto createProduct(final ProductDto productDto) throws Exception;

    ProductDto updateProduct(final ProductDto productDto) throws Exception;

    void deleteProduct(final Long id) throws Exception;
}

package vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.ObjectState;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.product.ProductDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Product;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.BadRequestException;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper.ProductMapper;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.product.ProductCustomRepository;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductCustomRepository productCustomRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(final ProductCustomRepository productCustomRepository, final ProductMapper productMapper) {
        this.productCustomRepository = productCustomRepository;
        this.productMapper = productMapper;
    }


    @Override
    public List<ProductDto> getAll() {
        return productMapper.toProductDtos(productCustomRepository.getAll());
    }

    @Override
    public ProductDto getByProduct(Long id) throws BadRequestException {
        Product product = productCustomRepository.getByProduct(id)
                .orElseThrow(() -> new BadRequestException("Không tìm thấy sản phẩm có id: " + id));
        return productMapper.toProductDto(product);
    }

    @Transactional
    @Override
    public ProductDto createProduct(ProductDto productDto) throws BadRequestException {
        Product product = productCustomRepository.save(productMapper.toProduct(productDto));
        if (ObjectUtils.isEmpty(product)) {
            throw new BadRequestException("Product not found");
        }
        return productMapper.toProductDto(product);
    }

    @Transactional
    @Override
    public ProductDto updateProduct(ProductDto productDto) throws BadRequestException {
        Product product = productCustomRepository.getByProduct(productDto.getId())
                .orElseThrow(() -> new BadRequestException("Không tìm thấy sản phẩm có id: " + productDto.getId()));

        if (ObjectUtils.isEmpty(productCustomRepository.save(productMapper.toProduct(productDto)))) {
            throw new BadRequestException("Update không thành công");
        }

        return productMapper.toProductDto(product);
    }

    @Override
    public void deleteProduct(Long id) throws BadRequestException {
        Product product = productCustomRepository.getByProduct(id)
                .orElseThrow(() -> new BadRequestException("Không tìm thấy sản phẩm có id: " + id));

        product.setState(ObjectState.DELETED);
        product.setDeletedDate(ZonedDateTime.now());

        if (ObjectUtils.isEmpty(productCustomRepository.save(product))) {
            throw new BadRequestException("Xoá không thành công");
        }


    }
}

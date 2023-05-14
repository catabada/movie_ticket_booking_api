package vn.edu.hcmuaf.fit.movie_ticket_booking_api.service.combo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.combo.ComboDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.combo.ComboItemDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.product.ProductDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Combo;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.ComboItem;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Product;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.BadRequestException;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.exception.NotFoundException;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper.ComboMapper;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper.ProductMapper;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.combo.ComboCustomRepository;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.product.ProductCustomRepository;

import java.util.List;

@Service
public class ComboServiceImpl implements ComboService {
    private final ComboCustomRepository comboCustomRepository;
    private final ProductCustomRepository productCustomRepository;
    private final ProductMapper productMapper;
    private final ComboMapper comboMapper;

    @Autowired
    public ComboServiceImpl(final ComboCustomRepository comboCustomRepository,
                            final ProductCustomRepository productCustomRepository,
                            final ComboMapper comboMapper,
                            final ProductMapper productMapper) {
        this.comboCustomRepository = comboCustomRepository;
        this.productCustomRepository = productCustomRepository;
        this.comboMapper = comboMapper;
        this.productMapper = productMapper;
    }

    @Override
    public ComboDto getCombo(Long id) {
        return null;
    }

    @Override
    public List<ComboDto> getCombos() {
        return comboMapper.toComboDtos(comboCustomRepository.getCombos());
    }

    @Override
    @Transactional
    public ComboDto createCombo(ComboDto comboDto) throws BadRequestException {

        Combo combo = comboMapper.toCombo(comboDto);

        for(ComboItem item : combo.getComboItems()) {
            item.setCombo(combo);
        }

        combo.setComboItems(combo.getComboItems());

        combo = comboCustomRepository.saveAndFlush(combo);

        if (ObjectUtils.isEmpty(combo)) throw new BadRequestException("Tạo dữ liệu không thành công");

        return comboMapper.toComboDto(combo);
    }

    @Override
    public ComboDto updateCombo(ComboDto comboDto) {
        return null;
    }

    @Override
    public void deleteCombo(Long id) {

    }

    private ProductDto checkExistProduct(ComboItemDto comboItemDto) throws NotFoundException {
        Product product = productCustomRepository.findById(comboItemDto.getProduct().getId()).orElseThrow(
                () -> new NotFoundException("Không tìm thấy sản phẩm")
        );
        return productMapper.toProductDto(product);
    }
}

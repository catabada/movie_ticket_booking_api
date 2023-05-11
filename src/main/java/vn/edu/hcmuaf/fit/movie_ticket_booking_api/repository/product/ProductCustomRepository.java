package vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.product;

import org.springframework.data.repository.NoRepositoryBean;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Product;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.ICustomRepository;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface ProductCustomRepository extends ICustomRepository<Product, Long> {
    List<Product> getAll();

    Optional<Product> getByProduct(Long id);

}

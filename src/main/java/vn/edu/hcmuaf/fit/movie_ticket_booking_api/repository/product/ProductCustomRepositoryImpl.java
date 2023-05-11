package vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.product;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.constant.ObjectState;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Product;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.QProduct;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.AbstractCustomRepository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductCustomRepositoryImpl extends AbstractCustomRepository<Product, Long> implements ProductCustomRepository {
    final static QProduct qProduct = QProduct.product;

    protected ProductCustomRepositoryImpl(EntityManager entityManager) {
        super(Product.class, entityManager);
    }



    @Override
    public Optional<Product> getByProduct(Long id) {
        return Optional.ofNullable(
                queryFactory.selectFrom(qProduct)
                        .where(qProduct.id.eq(id).and(qProduct.state.ne(ObjectState.DELETED)))
                        .fetchOne()
        );
    }

    @Override
    public List<Product> getAll() {
        return queryFactory.selectFrom(qProduct)
                .where(qProduct.state.ne(ObjectState.DELETED))
                .fetch();
    }

    @Override
    public void saveAll() {

    }
}

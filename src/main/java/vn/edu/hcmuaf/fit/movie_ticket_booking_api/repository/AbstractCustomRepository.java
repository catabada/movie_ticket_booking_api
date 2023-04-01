package vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

public abstract class AbstractCustomRepository<T, ObjectKeyId> extends SimpleJpaRepository<T, ObjectKeyId> {
    protected JPAQueryFactory queryFactory;
    protected EntityManager entityManager;
    protected Class<T> domainClass;

    protected AbstractCustomRepository(final Class<T> domainClass, final EntityManager entityManager) {
        super(domainClass, entityManager);
        this.entityManager = entityManager;
        this.domainClass = domainClass;
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    private AbstractCustomRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }
}

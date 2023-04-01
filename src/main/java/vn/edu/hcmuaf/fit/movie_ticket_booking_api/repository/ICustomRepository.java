package vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ICustomRepository<T, ObjectKeyId> extends JpaRepository<T, ObjectKeyId> {
    void saveAll(ObjectKeyId id);
}

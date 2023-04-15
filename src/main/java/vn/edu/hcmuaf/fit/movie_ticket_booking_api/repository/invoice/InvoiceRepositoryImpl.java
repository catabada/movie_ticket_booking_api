package vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.invoice;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Invoice;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.QInvoice;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.repository.AbstractCustomRepository;

@Repository
public class InvoiceRepositoryImpl extends AbstractCustomRepository<Invoice, Long> implements InvoiceRepository {
    private final QInvoice qInvoice = QInvoice.invoice;

    protected InvoiceRepositoryImpl(EntityManager entityManager) {
        super(Invoice.class, entityManager);
    }


    @Override
    public void saveAll() {

    }
}

package vn.edu.hcmuaf.fit.movie_ticket_booking_api.mapper;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.dto.invoice.InvoiceComboDto;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.Invoice;
import vn.edu.hcmuaf.fit.movie_ticket_booking_api.entity.InvoiceCombo;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ComboMapper.class})
public interface InvoiceComboMapper {
    InvoiceComboDto toInvoiceComboDto(InvoiceComboDto invoiceComboDto);

    @Named("toInvoiceComboDtoWithoutInvoice")
    @Mapping(target = "invoice", ignore = true)
    @Mapping(target = "combo", source = "combo", qualifiedByName = "toComboDto")
    InvoiceComboDto toInvoiceComboDtoWithoutInvoice(InvoiceCombo invoiceCombo);

    InvoiceCombo toInvoiceCombo(InvoiceComboDto invoiceDto);

    List<InvoiceComboDto> toInvoiceComboDtoList(List<InvoiceComboDto> invoices);

    List<InvoiceCombo> toInvoiceComboList(List<InvoiceComboDto> invoices);
}

package sn.exam.payment.service;

import org.springframework.stereotype.Service;
import sn.exam.payment.entity.Bill;
import sn.exam.payment.exception.BillNotFoundException;
import sn.exam.payment.repository.BillRepository;
import sn.exam.shared.dto.BillResponse;

import java.time.LocalDate;
import java.util.List;

@Service
public class BillService {

    private final BillRepository billRepository;

    public BillService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    public BillResponse findBill(String provider, String billReference) {
        Bill bill = billRepository.findByProviderAndBillReference(provider, billReference)
                .orElseThrow(() -> new BillNotFoundException(provider, billReference));
        return toResponse(bill);
    }

    public List<BillResponse> findCurrentByProvider(String provider) {
        return billRepository.findByProvider(provider).stream()
                .filter(b -> !b.isPaid())
                .map(this::toResponse)
                .toList();
    }

    public List<BillResponse> findCurrentByProviderAndUnite(String provider, String unite) {
        return billRepository.findByProvider(provider).stream()
                .filter(b -> !b.isPaid())
                .filter(b -> b.getProvider().equalsIgnoreCase(unite))
                .map(this::toResponse)
                .toList();
    }

    public List<BillResponse> findByPeriode(LocalDate debut, LocalDate fin) {
        return billRepository.findByBillDateBetween(debut, fin).stream()
                .map(this::toResponse)
                .toList();
    }

    public List<BillResponse> findByReferences(List<String> references) {
        return billRepository.findByBillReferenceIn(references).stream()
                .map(this::toResponse)
                .toList();
    }

    private BillResponse toResponse(Bill bill) {
        return new BillResponse(
                bill.getBillReference(),
                bill.getProvider(),
                bill.getSubscriberName(),
                bill.getAmount(),
                bill.isPaid(),
                bill.getBillDate()
        );
    }
}

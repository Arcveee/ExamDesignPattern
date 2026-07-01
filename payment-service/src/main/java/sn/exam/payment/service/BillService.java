package sn.exam.payment.service;

import org.springframework.stereotype.Service;
import sn.exam.payment.entity.Bill;
import sn.exam.payment.exception.BillNotFoundException;
import sn.exam.payment.repository.BillRepository;
import sn.exam.shared.dto.BillResponse;

@Service
public class BillService {

    private final BillRepository billRepository;

    public BillService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    public BillResponse findBill(String provider, String billReference) {
        Bill bill = billRepository.findByProviderAndBillReference(provider, billReference)
                .orElseThrow(() -> new BillNotFoundException(provider, billReference));
        return new BillResponse(
                bill.getBillReference(),
                bill.getProvider(),
                bill.getSubscriberName(),
                bill.getAmount(),
                bill.isPaid()
        );
    }
}

package sn.exam.badwallet.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.exam.badwallet.dto.PayBillsByReferencesRequest;
import sn.exam.badwallet.dto.PayCurrentBillRequest;
import sn.exam.badwallet.dto.TransactionResponse;
import sn.exam.badwallet.service.PaymentFacadeService;

import java.util.List;

@RestController
@RequestMapping("/api/wallets")
public class BillPaymentController {

    private final PaymentFacadeService paymentFacadeService;

    public BillPaymentController(PaymentFacadeService paymentFacadeService) {
        this.paymentFacadeService = paymentFacadeService;
    }

    @PostMapping("/pay")
    public ResponseEntity<List<TransactionResponse>> payCurrentBills(
            @Valid @RequestBody PayCurrentBillRequest request) {
        return ResponseEntity.ok(paymentFacadeService.payCurrentBills(request));
    }

    @PostMapping("/pay-factures")
    public ResponseEntity<List<TransactionResponse>> payByReferences(
            @Valid @RequestBody PayBillsByReferencesRequest request) {
        return ResponseEntity.ok(paymentFacadeService.payBillsByReferences(request));
    }
}

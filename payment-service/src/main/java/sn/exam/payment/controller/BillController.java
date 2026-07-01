package sn.exam.payment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.exam.payment.service.BillService;
import sn.exam.shared.dto.BillResponse;

@RestController
@RequestMapping("/api/bills")
public class BillController {

    private final BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
    }

    @GetMapping("/{provider}/{reference}")
    public ResponseEntity<BillResponse> getBill(@PathVariable String provider,
                                                 @PathVariable String reference) {
        return ResponseEntity.ok(billService.findBill(provider, reference));
    }
}

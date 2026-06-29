package sn.exam.payment.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.exam.payment.service.BillService;
import sn.exam.shared.dto.BillResponse;
import sn.exam.shared.dto.BillsByReferencesRequest;

import java.util.List;

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

    @GetMapping("/{provider}/current")
    public ResponseEntity<List<BillResponse>> getCurrentBills(@PathVariable String provider) {
        return ResponseEntity.ok(billService.findCurrentByProvider(provider));
    }

    @PostMapping("/by-references")
    public ResponseEntity<List<BillResponse>> getByReferences(@RequestBody BillsByReferencesRequest request) {
        return ResponseEntity.ok(billService.findByReferences(request.references()));
    }
}

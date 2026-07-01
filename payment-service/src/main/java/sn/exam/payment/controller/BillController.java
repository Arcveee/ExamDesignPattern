package sn.exam.payment.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.exam.payment.service.BillService;
import sn.exam.shared.dto.BillResponse;
import sn.exam.shared.dto.BillsByReferencesRequest;

import java.time.LocalDate;
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
    public ResponseEntity<List<BillResponse>> getCurrentBills(
            @PathVariable String provider,
            @RequestParam(required = false) String unite) {
        if (unite != null) {
            return ResponseEntity.ok(billService.findCurrentByProviderAndUnite(provider, unite));
        }
        return ResponseEntity.ok(billService.findCurrentByProvider(provider));
    }

    @GetMapping("/periode")
    public ResponseEntity<List<BillResponse>> getByPeriode(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(billService.findByPeriode(debut, fin));
    }

    @PostMapping("/by-references")
    public ResponseEntity<List<BillResponse>> getByReferences(@RequestBody BillsByReferencesRequest request) {
        return ResponseEntity.ok(billService.findByReferences(request.references()));
    }
}

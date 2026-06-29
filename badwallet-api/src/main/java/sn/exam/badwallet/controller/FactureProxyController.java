package sn.exam.badwallet.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.exam.badwallet.client.FactureServiceClient;
import sn.exam.badwallet.dto.BillInfo;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/external/factures")
public class FactureProxyController {

    private final FactureServiceClient factureServiceClient;

    public FactureProxyController(FactureServiceClient factureServiceClient) {
        this.factureServiceClient = factureServiceClient;
    }

    @GetMapping("/{code}/current")
    public ResponseEntity<List<BillInfo>> getCurrent(
            @PathVariable String code,
            @RequestParam(required = false) String unite) {
        if (unite != null) {
            return ResponseEntity.ok(factureServiceClient.getCurrentFacturesByUnite(code, unite));
        }
        return ResponseEntity.ok(factureServiceClient.getCurrentFactures(code));
    }

    @GetMapping("/{code}/periode")
    public ResponseEntity<List<BillInfo>> getByPeriode(
            @PathVariable String code,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {
        return ResponseEntity.ok(factureServiceClient.getFacturesByPeriode(debut, fin));
    }
}

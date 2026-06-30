package sn.exam.badwallet.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.exam.badwallet.dto.DepositRequest;
import sn.exam.badwallet.dto.TransactionResponse;
import sn.exam.badwallet.service.DepositService;

@RestController
@RequestMapping("/api/wallets")
public class DepositController {

    private final DepositService depositService;

    public DepositController(DepositService depositService) {
        this.depositService = depositService;
    }

    @PostMapping("/{id}/deposit")
    public ResponseEntity<TransactionResponse> deposit(@PathVariable("id") Long id,
                                                        @Valid @RequestBody DepositRequest request) {
        return ResponseEntity.ok(depositService.deposit(id, request));
    }
}

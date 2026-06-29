package sn.exam.badwallet.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.exam.badwallet.dto.TransactionResponse;
import sn.exam.badwallet.dto.WithdrawRequest;
import sn.exam.badwallet.service.WithdrawService;

@RestController
@RequestMapping("/api/wallets")
public class WithdrawController {

    private final WithdrawService withdrawService;

    public WithdrawController(WithdrawService withdrawService) {
        this.withdrawService = withdrawService;
    }

    @PostMapping("/withdraw")
    public ResponseEntity<TransactionResponse> withdraw(@Valid @RequestBody WithdrawRequest request) {
        return ResponseEntity.ok(withdrawService.withdraw(request));
    }
}

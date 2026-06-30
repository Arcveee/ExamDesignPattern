package sn.exam.badwallet.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.exam.badwallet.dto.BalanceResponse;
import sn.exam.badwallet.dto.CreateWalletRequest;
import sn.exam.badwallet.dto.TransactionResponse;
import sn.exam.badwallet.dto.WalletResponse;
import sn.exam.badwallet.service.TransactionService;
import sn.exam.badwallet.service.WalletService;

@RestController
@RequestMapping("/api/wallets")
public class WalletController {

    private final WalletService walletService;
    private final TransactionService transactionService;

    public WalletController(WalletService walletService, TransactionService transactionService) {
        this.walletService = walletService;
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<WalletResponse> createWallet(@Valid @RequestBody CreateWalletRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(walletService.createWallet(request));
    }

    @GetMapping
    public ResponseEntity<Page<WalletResponse>> getAllWallets(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return ResponseEntity.ok(walletService.getAllWallets(pageable));
    }

    @GetMapping("/{phoneNumber}")
    public ResponseEntity<WalletResponse> getWallet(@PathVariable("phoneNumber") String phoneNumber) {
        return ResponseEntity.ok(walletService.getWalletByPhoneNumber(phoneNumber));
    }

    @GetMapping("/{phoneNumber}/balance")
    public ResponseEntity<BalanceResponse> getBalance(@PathVariable("phoneNumber") String phoneNumber) {
        return ResponseEntity.ok(walletService.getBalance(phoneNumber));
    }

    @GetMapping("/{phoneNumber}/transactions")
    public ResponseEntity<Page<TransactionResponse>> getTransactionHistory(
            @PathVariable("phoneNumber") String phoneNumber,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(transactionService.getHistory(phoneNumber, page, size));
    }
}

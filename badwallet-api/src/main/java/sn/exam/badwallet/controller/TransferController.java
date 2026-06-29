package sn.exam.badwallet.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.exam.badwallet.dto.TransactionResponse;
import sn.exam.badwallet.dto.TransferRequest;
import sn.exam.badwallet.service.TransferService;

@RestController
@RequestMapping("/api/wallets")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponse> transfer(@Valid @RequestBody TransferRequest request) {
        return ResponseEntity.ok(transferService.transfer(request));
    }
}

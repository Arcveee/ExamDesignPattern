package sn.exam.badwallet.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sn.exam.badwallet.service.SeedService;

@RestController
@RequestMapping("/api/wallets")
public class SeedController {

    private final SeedService seedService;

    public SeedController(SeedService seedService) {
        this.seedService = seedService;
    }

    @PostMapping("/seed")
    public ResponseEntity<String> seed(
            @RequestParam(defaultValue = "10") int numWallets,
            @RequestParam(defaultValue = "5") int eventsPerWallet) {
        seedService.seedWalletsAndTransactions(numWallets, eventsPerWallet);
        return ResponseEntity.accepted()
                .body("Seeding started: " + numWallets + " wallets × " + eventsPerWallet + " transactions");
    }
}

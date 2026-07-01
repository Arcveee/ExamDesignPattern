package sn.exam.badwallet.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.exam.badwallet.entity.Wallet;
import sn.exam.badwallet.factory.TransactionFactory;
import sn.exam.badwallet.factory.WalletFactory;
import sn.exam.badwallet.repository.TransactionRepository;
import sn.exam.badwallet.repository.WalletRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class SeedService {

    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;
    private final WalletFactory walletFactory;
    private final TransactionFactory transactionFactory;

    public SeedService(WalletRepository walletRepository,
                       TransactionRepository transactionRepository,
                       WalletFactory walletFactory,
                       TransactionFactory transactionFactory) {
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
        this.walletFactory = walletFactory;
        this.transactionFactory = transactionFactory;
    }

    @Async("seedExecutor")
    @Transactional
    public CompletableFuture<Void> seedWalletsAndTransactions(int numWallets, int eventsPerWallet) {
        List<Wallet> savedWallets = new ArrayList<>();

        for (int i = 0; i < numWallets; i++) {
            savedWallets.add(walletRepository.save(walletFactory.createRandom()));
        }

        for (Wallet wallet : savedWallets) {
            for (int j = 0; j < eventsPerWallet; j++) {
                transactionRepository.save(transactionFactory.createRandom(wallet, savedWallets));
            }
        }

        return CompletableFuture.completedFuture(null);
    }
}

package sn.exam.badwallet.service;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.exam.badwallet.dto.TransactionResponse;
import sn.exam.badwallet.dto.TransferRequest;
import sn.exam.badwallet.entity.Transaction;
import sn.exam.badwallet.entity.Wallet;
import sn.exam.badwallet.exception.InsufficientBalanceException;
import sn.exam.badwallet.exception.InvalidTransactionException;
import sn.exam.badwallet.exception.WalletNotFoundException;
import sn.exam.badwallet.mapper.TransactionMapper;
import sn.exam.badwallet.pattern.template.AbstractTransactionProcessor;
import sn.exam.badwallet.repository.TransactionRepository;
import sn.exam.badwallet.repository.WalletRepository;
import sn.exam.shared.enums.TransactionStatus;
import sn.exam.shared.enums.TransactionType;

import java.math.BigDecimal;

@Service
public class TransferService extends AbstractTransactionProcessor {

    public TransferService(WalletRepository walletRepository,
                           TransactionRepository transactionRepository,
                           TransactionMapper transactionMapper,
                           ApplicationEventPublisher eventPublisher) {
        super(walletRepository, transactionRepository, transactionMapper, eventPublisher);
    }

    @Transactional
    public TransactionResponse transfer(TransferRequest request) {
        Wallet sender = walletRepository.findByPhoneNumber(request.senderPhone())
                .orElseThrow(() -> new WalletNotFoundException(request.senderPhone()));
        Wallet receiver = walletRepository.findByPhoneNumber(request.receiverPhone())
                .orElseThrow(() -> new WalletNotFoundException(request.receiverPhone()));
        return process(sender, receiver, request.amount());
    }

    @Override
    protected void validateTransfer(Wallet source, Wallet target, BigDecimal amount) {
        if (source.getId().equals(target.getId())) {
            throw new InvalidTransactionException("sender and receiver must be different");
        }
        if (source.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException(source.getBalance(), amount);
        }
    }

    @Override
    protected Transaction executeTransfer(Wallet source, Wallet target, BigDecimal amount) {
        source.setBalance(source.getBalance().subtract(amount));
        target.setBalance(target.getBalance().add(amount));
        return Transaction.builder()
                .type(TransactionType.TRANSFER)
                .amount(amount)
                .fee(BigDecimal.ZERO)
                .status(TransactionStatus.SUCCESS)
                .sourceWallet(source)
                .targetWallet(target)
                .build();
    }

    @Override
    protected void validate(Wallet wallet, BigDecimal amount) {}

    @Override
    protected Transaction execute(Wallet wallet, BigDecimal amount) {
        return null;
    }
}

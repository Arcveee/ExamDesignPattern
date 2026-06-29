package sn.exam.badwallet.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.exam.badwallet.dto.TransactionResponse;
import sn.exam.badwallet.entity.Wallet;
import sn.exam.badwallet.exception.WalletNotFoundException;
import sn.exam.badwallet.mapper.TransactionMapper;
import sn.exam.badwallet.repository.TransactionRepository;
import sn.exam.badwallet.repository.WalletRepository;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final WalletRepository walletRepository;
    private final TransactionMapper transactionMapper;

    public TransactionService(TransactionRepository transactionRepository,
                               WalletRepository walletRepository,
                               TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
        this.transactionMapper = transactionMapper;
    }

    @Transactional(readOnly = true)
    public Page<TransactionResponse> getHistory(String phoneNumber, int page, int size) {
        Wallet wallet = walletRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new WalletNotFoundException(phoneNumber));
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return transactionRepository
                .findBySourceWalletIdOrTargetWalletId(wallet.getId(), wallet.getId(), pageable)
                .map(transactionMapper::toResponse);
    }
}

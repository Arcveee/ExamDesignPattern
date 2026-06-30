package sn.exam.badwallet.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.exam.badwallet.dto.BalanceResponse;
import sn.exam.badwallet.dto.CreateWalletRequest;
import sn.exam.badwallet.dto.WalletResponse;
import sn.exam.badwallet.entity.Wallet;
import sn.exam.badwallet.exception.WalletAlreadyExistsException;
import sn.exam.badwallet.exception.WalletNotFoundException;
import sn.exam.badwallet.mapper.WalletMapper;
import sn.exam.badwallet.repository.WalletRepository;

@Service
@SuppressWarnings("null")
public class WalletService {

    private final WalletRepository walletRepository;
    private final WalletMapper walletMapper;

    public WalletService(WalletRepository walletRepository, WalletMapper walletMapper) {
        this.walletRepository = walletRepository;
        this.walletMapper = walletMapper;
    }

    @Transactional
    public WalletResponse createWallet(CreateWalletRequest request) {
        String phone = normalizePhone(request.phoneNumber());
        if (walletRepository.existsByPhoneNumber(phone)) {
            throw new WalletAlreadyExistsException("phoneNumber", phone);
        }
        if (walletRepository.existsByCode(request.code())) {
            throw new WalletAlreadyExistsException("code", request.code());
        }

        Wallet wallet = Wallet.builder()
                .phoneNumber(phone)
                .ownerName(request.ownerName())
                .code(request.code())
                .balance(request.initialBalance())
                .currency(request.currency())
                .build();

        return walletMapper.toResponse(walletRepository.save(wallet));
    }

    @Transactional(readOnly = true)
    public Page<WalletResponse> getAllWallets(Pageable pageable) {
        return walletRepository.findAll(pageable).map(walletMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public WalletResponse getWalletByPhoneNumber(String phoneNumber) {
        return walletMapper.toResponse(findOrThrow(normalizePhone(phoneNumber)));
    }

    @Transactional(readOnly = true)
    public BalanceResponse getBalance(String phoneNumber) {
        Wallet wallet = findOrThrow(normalizePhone(phoneNumber));
        return new BalanceResponse(wallet.getBalance(), wallet.getCurrency());
    }

    private Wallet findOrThrow(String phoneNumber) {
        return walletRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new WalletNotFoundException(phoneNumber));
    }

    /**
     * Normalise le numéro de téléphone : supprime les espaces et ajoute +221 si nécessaire.
     */
    private String normalizePhone(String phoneNumber) {
        if (phoneNumber == null) return null;
        String clean = phoneNumber.replaceAll("\\s+", "");
        if (clean.length() == 9 && !clean.startsWith("+")) {
            return "+221" + clean;
        }
        return clean;
    }
}

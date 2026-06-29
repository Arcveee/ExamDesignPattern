package sn.exam.badwallet.mapper;

import org.springframework.stereotype.Component;
import sn.exam.badwallet.dto.WalletResponse;
import sn.exam.badwallet.entity.Wallet;

@Component
public class WalletMapper {

    public WalletResponse toResponse(Wallet wallet) {
        return new WalletResponse(
                wallet.getId(),
                wallet.getPhoneNumber(),
                wallet.getEmail(),
                wallet.getBalance(),
                wallet.getCurrency(),
                wallet.getCreatedAt()
        );
    }
}

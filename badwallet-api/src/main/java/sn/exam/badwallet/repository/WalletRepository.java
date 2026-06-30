package sn.exam.badwallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.exam.badwallet.entity.Wallet;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByPhoneNumber(String phoneNumber);
    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByCode(String code);
}

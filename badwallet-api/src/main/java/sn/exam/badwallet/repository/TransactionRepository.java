package sn.exam.badwallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.exam.badwallet.entity.Transaction;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findBySourceWalletIdOrTargetWalletIdOrderByCreatedAtDesc(Long sourceId, Long targetId);
}

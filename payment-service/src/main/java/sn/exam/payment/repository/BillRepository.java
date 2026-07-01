package sn.exam.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.exam.payment.entity.Bill;

import java.util.Optional;

public interface BillRepository extends JpaRepository<Bill, Long> {
    Optional<Bill> findByProviderAndBillReference(String provider, String billReference);
}

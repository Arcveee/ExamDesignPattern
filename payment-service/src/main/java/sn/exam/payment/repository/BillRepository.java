package sn.exam.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.exam.payment.entity.Bill;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BillRepository extends JpaRepository<Bill, Long> {
    Optional<Bill> findByProviderAndBillReference(String provider, String billReference);
    List<Bill> findByProvider(String provider);
    List<Bill> findByBillReferenceIn(List<String> references);
    List<Bill> findByBillDateBetween(LocalDate debut, LocalDate fin);
    List<Bill> findByProviderAndBillDateBetween(String provider, LocalDate debut, LocalDate fin);
}

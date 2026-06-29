package sn.exam.payment.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "bills")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String billReference;

    @Column(nullable = false)
    private String provider;

    @Column(nullable = false)
    private String subscriberName;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private boolean paid;

    @Column(nullable = false)
    private LocalDate billDate;

    protected Bill() {}

    public Long getId() { return id; }
    public String getBillReference() { return billReference; }
    public String getProvider() { return provider; }
    public String getSubscriberName() { return subscriberName; }
    public BigDecimal getAmount() { return amount; }
    public boolean isPaid() { return paid; }
    public LocalDate getBillDate() { return billDate; }
    public void setPaid(boolean paid) { this.paid = paid; }
}

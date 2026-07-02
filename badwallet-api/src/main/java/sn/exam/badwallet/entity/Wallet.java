package sn.exam.badwallet.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "wallets")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private String ownerName;

    @Column(nullable = false, unique = true, length = 20)
    private String code;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal balance;

    @Column(nullable = false, length = 3)
    private String currency;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected Wallet() {}

    private Wallet(Builder builder) {
        this.phoneNumber = builder.phoneNumber;
        this.ownerName = builder.ownerName;
        this.code = builder.code;
        this.balance = builder.balance;
        this.currency = builder.currency;
        this.createdAt = builder.createdAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String phoneNumber;
        private String ownerName;
        private String code;
        private BigDecimal balance = BigDecimal.ZERO;
        private String currency = "XOF";
        private LocalDateTime createdAt = LocalDateTime.now();

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder ownerName(String ownerName) {
            this.ownerName = ownerName;
            return this;
        }

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder balance(BigDecimal balance) {
            this.balance = balance;
            return this;
        }

        public Builder currency(String currency) {
            this.currency = currency;
            return this;
        }

        public Wallet build() {
            return new Wallet(this);
        }
    }

    public Long getId() { return id; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getOwnerName() { return ownerName; }
    public String getCode() { return code; }
    public BigDecimal getBalance() { return balance; }
    public String getCurrency() { return currency; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setBalance(BigDecimal balance) { this.balance = balance; }
}

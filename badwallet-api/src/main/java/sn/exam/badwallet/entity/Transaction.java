package sn.exam.badwallet.entity;

import jakarta.persistence.*;
import sn.exam.shared.enums.TransactionStatus;
import sn.exam.shared.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal fee;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_wallet_id")
    private Wallet sourceWallet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_wallet_id")
    private Wallet targetWallet;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected Transaction() {}

    private Transaction(Builder builder) {
        this.type = builder.type;
        this.amount = builder.amount;
        this.fee = builder.fee;
        this.status = builder.status;
        this.sourceWallet = builder.sourceWallet;
        this.targetWallet = builder.targetWallet;
        this.createdAt = builder.createdAt;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private TransactionType type;
        private BigDecimal amount;
        private BigDecimal fee = BigDecimal.ZERO;
        private TransactionStatus status = TransactionStatus.PENDING;
        private Wallet sourceWallet;
        private Wallet targetWallet;
        private LocalDateTime createdAt = LocalDateTime.now();

        public Builder type(TransactionType type) {
            this.type = type;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder fee(BigDecimal fee) {
            this.fee = fee;
            return this;
        }

        public Builder status(TransactionStatus status) {
            this.status = status;
            return this;
        }

        public Builder sourceWallet(Wallet sourceWallet) {
            this.sourceWallet = sourceWallet;
            return this;
        }

        public Builder targetWallet(Wallet targetWallet) {
            this.targetWallet = targetWallet;
            return this;
        }

        public Transaction build() {
            return new Transaction(this);
        }
    }

    public Long getId() { return id; }
    public TransactionType getType() { return type; }
    public BigDecimal getAmount() { return amount; }
    public BigDecimal getFee() { return fee; }
    public TransactionStatus getStatus() { return status; }
    public Wallet getSourceWallet() { return sourceWallet; }
    public Wallet getTargetWallet() { return targetWallet; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setStatus(TransactionStatus status) { this.status = status; }
}

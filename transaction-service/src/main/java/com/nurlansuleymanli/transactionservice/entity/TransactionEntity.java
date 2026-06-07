package com.nurlansuleymanli.transactionservice.entity;

import com.nurlansuleymanli.transactionservice.model.enums.TransactionStatus;
import com.nurlansuleymanli.transactionservice.model.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "transactions")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    Long cardId;

    @Column(nullable = false, precision = 19, scale = 4)
    BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    TransactionType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    TransactionStatus status;

    @Column(length = 500)
    String description;

    @Column(nullable = false)
    LocalDateTime transactionDate;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    LocalDateTime updatedAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    LocalDateTime createdAt;

}

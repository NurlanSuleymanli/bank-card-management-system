package com.nurlansuleymanli.cardservice.entity;

import com.nurlansuleymanli.cardservice.modul.enums.CardType;
import com.nurlansuleymanli.cardservice.modul.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cards")
public class CardEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "card_number",length = 16, nullable = false, unique = true, updatable = false)
    String cardNumber;

    @Column(name = "customer_id", nullable = false)
    Long customerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_type", nullable = false)
    CardType cardType;

    @Column(name = "balance", nullable = false, precision = 19, scale = 2)
    BigDecimal balance;

    @Column(name = "credit_limit", precision = 19, scale = 2)
    BigDecimal creditLimit;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    Status status;

    @Column(name = "expiry_date", nullable = false)
    LocalDate expiryDate;

    @Column(name = "cvv",nullable = false, length = 60)
    String cvv;

    @Column(name = "created_at",nullable = false, updatable = false)
    @CreationTimestamp
    LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    LocalDateTime updatedAt;



}

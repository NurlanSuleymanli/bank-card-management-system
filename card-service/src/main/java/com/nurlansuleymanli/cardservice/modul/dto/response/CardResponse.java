package com.nurlansuleymanli.cardservice.modul.dto.response;

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

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class CardResponse {

    Long id;
    String cardNumber;
    Long customerId;
    CardType cardType;
    BigDecimal balance;
    BigDecimal creditLimit;
    LocalDate expiryDate;

}

package com.nurlansuleymanli.cardservice.modul.dto.response;

import com.nurlansuleymanli.cardservice.modul.enums.TransactionStatus;
import com.nurlansuleymanli.cardservice.modul.enums.TransactionType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionResponse {

    Long id;
    Long cardId;
    BigDecimal amount;
    TransactionType type;
    TransactionStatus status;
    String description;
    LocalDateTime transactionDate;
    LocalDateTime createdAt;
}
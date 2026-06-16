package com.nurlansuleymanli.transactionservice.model.dto.response;

import com.nurlansuleymanli.transactionservice.model.enums.TransactionStatus;
import com.nurlansuleymanli.transactionservice.model.enums.TransactionType;
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

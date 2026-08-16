package com.nurlansuleymanli.cardservice.modul.dto.request;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionRequest {

    @NotNull
    Long cardId;
    @NotNull
    @DecimalMin(value = "0.01")
    BigDecimal amount;
    @NotNull
    TransactionType type;
    @NotBlank
    String description;
    @NotNull
    LocalDateTime transactionDate;
}

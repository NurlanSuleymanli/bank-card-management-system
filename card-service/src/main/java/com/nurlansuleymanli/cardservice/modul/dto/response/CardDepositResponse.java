package com.nurlansuleymanli.cardservice.modul.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class CardDepositResponse {

    String message;
    BigDecimal newBalance;
    LocalDateTime dateTime;
}

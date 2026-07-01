package com.nurlansuleymanli.cardservice.modul.dto.response;

import com.nurlansuleymanli.cardservice.modul.enums.PaymentStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class CardDepositResponse {

    String message;
    Double newBalance;
    LocalDateTime dateTime;
}

package com.nurlansuleymanli.cardservice.modul.dto.response;

import com.nurlansuleymanli.cardservice.modul.enums.CardType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;
import java.time.LocalDate;

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

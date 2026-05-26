package com.nurlansuleymanli.cardservice.modul.dto.response;

import com.nurlansuleymanli.cardservice.modul.enums.CardType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateCardResponse {

    Long id;
    String cardNumber;
    Long customerId;
    CardType cardType;
    BigDecimal balance;
    LocalDate expiryDate;

}

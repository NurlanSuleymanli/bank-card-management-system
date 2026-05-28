package com.nurlansuleymanli.cardservice.modul.dto.request;


import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CardPaymentRequest {

    BigDecimal amount;

}

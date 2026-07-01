package com.nurlansuleymanli.cardservice.modul.dto.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CardDepositRequest {

    @NotNull
    @Positive
    BigDecimal amount;
}

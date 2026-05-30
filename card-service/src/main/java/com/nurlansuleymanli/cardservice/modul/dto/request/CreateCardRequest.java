package com.nurlansuleymanli.cardservice.modul.dto.request;

import com.nurlansuleymanli.cardservice.modul.enums.CardType;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateCardRequest {

    @NotNull
    Long customerId;

    @NotNull
    CardType cardType;

}

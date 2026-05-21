package com.nurlansuleymanli.cardservice.modul.dto.request;

import com.nurlansuleymanli.cardservice.modul.enums.CardType;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateCardRequest {

    @NotBlank
    Long customerId;

    @NotBlank
    CardType cardType;

}

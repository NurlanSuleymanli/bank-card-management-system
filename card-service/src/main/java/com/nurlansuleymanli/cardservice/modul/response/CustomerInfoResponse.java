package com.nurlansuleymanli.cardservice.modul.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class CustomerInfoResponse {

    Long id;

    String firstName;

    String lastName;

    String email;

    String phoneNumber;


}

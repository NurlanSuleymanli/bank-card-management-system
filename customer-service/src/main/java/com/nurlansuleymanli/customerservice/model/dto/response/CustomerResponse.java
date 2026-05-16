package com.nurlansuleymanli.customerservice.model.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {

    Long id;

    String firstName;

    String lastName;

    String email;

    String phoneNumber;
}

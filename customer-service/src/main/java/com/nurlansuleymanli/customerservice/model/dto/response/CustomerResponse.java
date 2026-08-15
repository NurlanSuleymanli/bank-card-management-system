package com.nurlansuleymanli.customerservice.model.dto.response;

import com.nurlansuleymanli.customerservice.model.enums.Status;
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

    Status status;
}

package com.nurlansuleymanli.customerservice.model.enums.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {

    @NotBlank
    @Size(max = 255)
    String firstName;

    @NotBlank
    @Size(max = 255)
    String lastName;

    @NotBlank
    @Email
    String email;

    @NotBlank
    String pin;



}

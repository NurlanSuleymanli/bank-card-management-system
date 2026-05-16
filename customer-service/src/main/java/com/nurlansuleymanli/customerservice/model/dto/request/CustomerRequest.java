package com.nurlansuleymanli.customerservice.model.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
    @Size(max = 32)
    @Pattern(regexp = "^\\+994(10|50|51|55|70|77|99)\\d{7}$")
    String phoneNumber;

    @NotBlank
    @Size(max = 255)
    String lastName;

    @NotBlank
    @Email
    String email;

    @NotBlank
    @Pattern(regexp = "^[A-Z0-9]{7}$")
    String pin;



}

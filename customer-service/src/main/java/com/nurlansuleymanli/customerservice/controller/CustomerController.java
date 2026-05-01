package com.nurlansuleymanli.customerservice.controller;


import com.nurlansuleymanli.customerservice.model.enums.dto.request.CustomerRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CustomerController {

    @PostMapping("/customers")
    public ResponseEntity<?> createCustomer(@RequestBody CustomerRequest request){

        return null;
    }


}

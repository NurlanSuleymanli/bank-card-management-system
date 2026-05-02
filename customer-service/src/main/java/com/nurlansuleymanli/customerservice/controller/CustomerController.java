package com.nurlansuleymanli.customerservice.controller;


import com.nurlansuleymanli.customerservice.model.enums.dto.request.CustomerRequest;
import com.nurlansuleymanli.customerservice.model.enums.dto.request.response.CustomerResponse;
import com.nurlansuleymanli.customerservice.service.CustomerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CustomerController {

    CustomerService customerService;

    @PostMapping("/customers")
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CustomerRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomer(request));
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable Long id){
        return ResponseEntity.ok(customerService.getCustomer(id));
    }

}

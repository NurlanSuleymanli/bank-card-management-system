package com.nurlansuleymanli.customerservice.controller;


import com.nurlansuleymanli.customerservice.model.enums.dto.request.CustomerRequest;
import com.nurlansuleymanli.customerservice.model.enums.dto.request.UpdateCustomerRequest;
import com.nurlansuleymanli.customerservice.model.enums.dto.request.response.CustomerResponse;
import com.nurlansuleymanli.customerservice.service.CustomerService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@RestController
@RequestMapping("/api/v1")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class CustomerController {

    CustomerService customerService;

    @PostMapping("/customers")
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomer(request));
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable Long id){
        return ResponseEntity.ok(customerService.getCustomer(id));
    }

    @GetMapping("/customers")
    public ResponseEntity<Page<CustomerResponse>> getAllCustomers
            (@RequestParam(defaultValue = "0") int page,
             @RequestParam(defaultValue = "10") int size){

        return ResponseEntity.ok(customerService.getAllCustomers(page, size));
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable Long id, @Valid @RequestBody UpdateCustomerRequest request){
        return ResponseEntity.ok(customerService.updateCustomer(id,request));
    }


}

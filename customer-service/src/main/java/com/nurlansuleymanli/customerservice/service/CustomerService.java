package com.nurlansuleymanli.customerservice.service;

import com.nurlansuleymanli.customerservice.model.enums.dto.request.CustomerRequest;
import com.nurlansuleymanli.customerservice.model.enums.dto.request.response.CustomerResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerService {

    public CustomerResponse createCustomer(@Valid CustomerRequest request){

    }

}

package com.nurlansuleymanli.customerservice.service;

import com.nurlansuleymanli.customerservice.entity.CustomerEntity;
import com.nurlansuleymanli.customerservice.exception.CustomerExistException;
import com.nurlansuleymanli.customerservice.mapper.CustomerMapper;
import com.nurlansuleymanli.customerservice.model.enums.Status;
import com.nurlansuleymanli.customerservice.model.enums.dto.request.CustomerRequest;
import com.nurlansuleymanli.customerservice.model.enums.dto.request.response.CustomerResponse;
import com.nurlansuleymanli.customerservice.repository.CustomerRepository;
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

    CustomerRepository customerRepository;
    CustomerMapper customerMapper;

    public CustomerResponse createCustomer(@Valid CustomerRequest request){
        if (customerRepository.findByEmail(request.getEmail()).isPresent()){
            throw new CustomerExistException("Customer is exist!");
        }

        CustomerEntity customer= CustomerEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .status(Status.ACTIVE)
                .pin(request.getPin())
                .build();

        customerRepository.save(customer);

        return customerMapper.toCustomerResponse(customer);

    }

}

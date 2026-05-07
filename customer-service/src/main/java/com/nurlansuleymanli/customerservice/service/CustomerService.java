package com.nurlansuleymanli.customerservice.service;

import com.nurlansuleymanli.customerservice.entity.CustomerEntity;
import com.nurlansuleymanli.customerservice.exception.CustomerExistException;
import com.nurlansuleymanli.customerservice.exception.CustomerNotFoundException;
import com.nurlansuleymanli.customerservice.exception.EmailAlreadyExistException;
import com.nurlansuleymanli.customerservice.mapper.CustomerMapper;
import com.nurlansuleymanli.customerservice.model.enums.Status;
import com.nurlansuleymanli.customerservice.model.dto.request.CustomerRequest;
import com.nurlansuleymanli.customerservice.model.dto.request.UpdateCustomerRequest;
import com.nurlansuleymanli.customerservice.model.dto.response.CustomerResponse;
import com.nurlansuleymanli.customerservice.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
                .isActive(true)
                .pin(request.getPin())
                .build();

        customerRepository.save(customer);

        return customerMapper.toCustomerResponse(customer);

    }


    public CustomerResponse getCustomer(Long id){
        if(customerRepository.findById(id).isEmpty()){
            throw new CustomerNotFoundException("Customer not found!");
        }

        return customerMapper.toCustomerResponse(customerRepository.findById(id).get());

    }

    public Page<CustomerResponse> getAllCustomers(int page, int size){
        Pageable pageable = PageRequest.of(page,size);
        return customerRepository.findAll(pageable).map((customerMapper::toCustomerResponse));
    }

    public CustomerResponse updateCustomer(Long id,UpdateCustomerRequest request){
        CustomerEntity customerEntity= customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("Customer not found!"));

        if(request.getEmail() != null && customerEntity.getEmail().equals(request.getEmail())){
            if(customerRepository.findByEmail(request.getEmail()).isPresent()){
                throw new EmailAlreadyExistException("Email is taken!");
            }
            customerEntity.setEmail(request.getEmail());

        }

        if(request.getPhoneNumber()!=null && customerEntity.getPhoneNumber().equals(request.getPhoneNumber())){
            customerEntity.setPhoneNumber(request.getPhoneNumber());
        }

        customerRepository.save(customerEntity);

        return customerMapper.toCustomerResponse(customerEntity);




    }

}

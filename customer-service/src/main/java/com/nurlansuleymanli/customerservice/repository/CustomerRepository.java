package com.nurlansuleymanli.customerservice.repository;


import com.nurlansuleymanli.customerservice.entity.CustomerEntity;
import com.nurlansuleymanli.customerservice.model.enums.dto.request.CustomerRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

    Optional<CustomerEntity> findByEmail(String email);


}

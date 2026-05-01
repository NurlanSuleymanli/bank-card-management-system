package com.nurlansuleymanli.customerservice.mapper;

import com.nurlansuleymanli.customerservice.entity.CustomerEntity;
import com.nurlansuleymanli.customerservice.model.enums.dto.request.response.CustomerResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerResponse toCustomerResponse(CustomerEntity customerEntity);


}
